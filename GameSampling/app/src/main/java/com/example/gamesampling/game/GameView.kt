package com.example.gamesampling.game

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.gamesampling.R
import kotlin.math.floor
import kotlin.math.round

class GameView : View {

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private lateinit var paint: Paint
    private lateinit var textPaint: Paint
    private var combatAircraft: CombatAircraft? = null
    private val sprites = arrayListOf<Sprite>()
    private val spritesNeedAdded = arrayListOf<Sprite>()

    //0:combatAircraft
    //1:explosion
    //2:yellowBullet
    //3:blueBullet
    //4:smallEnemyPlane
    //5:middleEnemyPlane
    //6:bigEnemyPlane
    //7:bombAward
    //8:bulletAward
    //9:pause1
    //10:pause2
    //11:bomb
    private val bitmaps: ArrayList<Bitmap> = arrayListOf()

    // 要確認
    private var density: Float = resources.displayMetrics.density
    private var status: Int = STATUS_GAME_DESTROYED
    private var frame: Long = 0
    private var score: Long = 0

    private var fontSize: Float = 12f
    private var fontSize2: Float = 20f
    private var borderSize: Float = 2f
    private var continueRect: Rect = Rect()

    private var lastSingleClickTime: Long = -1
    private var touchDownTime: Long = -1
    private var touchUpTime: Long = -1
    private var touchX: Float = -1f
    private var touchY: Float = -1f

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        attrs?.let {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(it, R.styleable.GameView, defStyle, 0)
            typedArray.recycle()
        }
        // 初期化paint
        paint = Paint()
        paint.style = Paint.Style.FILL
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.FAKE_BOLD_TEXT_FLAG)
        textPaint.color = 0xff000000.toInt()
        fontSize = textPaint.textSize
        fontSize *= density
        fontSize2 *= density
        textPaint.textSize = fontSize
        borderSize *= density
    }

    fun start(bitmapIds: Array<Int>) {
        destroy()
        for (bitmapId in bitmapIds) {
            val bitmap = BitmapFactory.decodeResource(resources, bitmapId)
            bitmaps.add(bitmap)
        }
        startWhenBitmapsReady()
    }


    private fun startWhenBitmapsReady() {
        combatAircraft = CombatAircraft(bitmaps[0])
        status = STATUS_GAME_STARTED
        postInvalidate()
    }

    private fun restart() {
        destroyNotRecycleBitmaps()
        startWhenBitmapsReady()
    }

    fun pause() {
        status = STATUS_GAME_PAUSED
    }

    private fun resume() {
        status = STATUS_GAME_STARTED
        postInvalidate()
    }

    private fun getScore(): Long = this.score

    /*-------------------------------draw-------------------------------------*/

    override fun onDraw(canvas: Canvas?) {
//        if (isSingleClick()) {
        onSingleClick(touchX, touchY)
//        }
        super.onDraw(canvas)
        canvas?.let {
            when (status) {
                STATUS_GAME_STARTED -> {
                    drawGameStarted(it)
                }
                STATUS_GAME_PAUSED -> {
                    drawGamePaused(it)
                }
                STATUS_GAME_OVER -> {
                    drawGameOver(it)
                }
            }
        }
    }

    private fun drawGameStarted(canvas: Canvas) {
        drawScoreAndBombs(canvas)
        if (frame == 0L) {
            combatAircraft?.let {
                val centerX = canvas.width / 2
                val centerY = canvas.height - it.getHeight() / 2
                it.centerTo(centerX.toFloat(), centerY.toFloat())
            }
        }
        if (spritesNeedAdded.size > 0) {
            sprites.addAll(spritesNeedAdded)
            spritesNeedAdded.clear()
        }
        destroyBulletsFrontOfCombatAircraft()
        removeDestroyedSprites()
        if (frame % 30 == 0L) {
            createRandomSprites(canvas.width)
        }
        frame++
        val iterator = sprites.iterator()
        while (iterator.hasNext()) {
            val s = iterator.next()
            if (!s.isDestroyed()) {
                s.draw(canvas, paint, this)
            }
            if (s.isDestroyed()) {
                iterator.remove()
            }
        }

        if (combatAircraft != null) {
            combatAircraft!!.draw(canvas, paint, this)
            if (combatAircraft!!.isDestroyed()) {
                status = STATUS_GAME_OVER
            }
            postInvalidate()
        }
    }

    private fun drawGamePaused(canvas: Canvas) {
        drawScoreAndBombs(canvas)

        for (s in sprites) {
            s.onDraw(canvas, paint, this)
        }
        if (combatAircraft != null) {
            combatAircraft!!.onDraw(canvas, paint, this)
        }

        drawScoreDialog(canvas, "GamePause")

        if (lastSingleClickTime > 0) {
            postInvalidate()
        }
    }

    private fun drawGameOver(canvas: Canvas) {
        drawScoreDialog(canvas, "GameOver")

        if (lastSingleClickTime > 0) {
            postInvalidate()
        }
    }

    private fun drawScoreDialog(canvas: Canvas, operation: String) {
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height

        val originalFontSize: Float = textPaint.textSize
        val originalFontAlign: Paint.Align = textPaint.textAlign
        val originalColor: Int = paint.color
        val originalStyle: Paint.Style = paint.style

        /*
       W = 360
       w1 = 20
       w2 = 320
       buttonWidth = 140
       buttonHeight = 42
       H = 558
       h1 = 150
       h2 = 60
       h3 = 124
       h4 = 76
       */
        val w1 = (20.0 / 360.0 * canvasWidth)
        val w2 = canvasWidth - 2 * w1
        val buttonWidth = (140.0 / 360.0 * canvasWidth)

        val h1 = (150.0 / 558.0 * canvasHeight)
        val h2 = (60.6 / 558.0 * canvasHeight)
        val h3 = (124.0 / 558.0 * canvasHeight)
        val h4 = (76.0 / 558.0 * canvasHeight)
        val buttonHeight = (42.0 / 558.0 * canvasHeight)

        canvas.translate(w1.toFloat(), h2.toFloat())

        paint.style = Paint.Style.FILL
        paint.color = 0xFFD7DDDE.toInt()
        val rect1 = Rect(0, 0, w2.toInt(), (canvasHeight - 2 * h1).toInt())
        canvas.drawRect(rect1, paint)

        paint.style = Paint.Style.STROKE
        paint.color = 0xFF515151.toInt()
        paint.strokeWidth = borderSize
        paint.strokeJoin = Paint.Join.ROUND
        canvas.drawRect(rect1, paint)

        textPaint.textSize = fontSize2
        textPaint.textAlign = Paint.Align.CENTER
        canvas.drawText(
            "항공기 점수",
            (w2 / 2).toFloat(), ((h2 - fontSize2) / 2 + fontSize2).toFloat(), textPaint
        )
        canvas.translate(0f, h2.toFloat())
        canvas.drawLine(0f, 0f, w2.toFloat(), 0f, paint)

        val allScore = "${getScore()}"
        canvas.drawText(
            allScore,
            (w2 / 2).toFloat(),
            ((h3 - fontSize2) / 2 + fontSize2).toFloat(),
            textPaint
        )
        canvas.translate(0f, h3.toFloat())
        canvas.drawLine(0f, 0f, w2.toFloat(), 0f, paint)

        val rect2 = Rect()
        rect2.left = ((w2 - buttonWidth) / 2).toInt()
        rect2.right = (w2 - rect2.left).toInt()
        rect2.top = ((h4 - buttonHeight) / 2).toInt()
        rect2.bottom = (h4 - rect2.top).toInt()
        canvas.drawRect(rect2, paint)
        canvas.translate(0f, rect2.top.toFloat())
        canvas.drawText(
            operation,
            (w2 / 2).toFloat(),
            ((buttonHeight - fontSize2) / 2 + fontSize2).toFloat(),
            textPaint
        )
        continueRect = Rect(rect2)
        continueRect.left = (w1 + rect2.left).toInt()
        continueRect.right = (continueRect.left + buttonWidth).toInt()
        continueRect.top = (h1 + h2 + h3 + rect2.top).toInt()
        continueRect.bottom = (continueRect.top + buttonHeight).toInt()

        textPaint.textSize = originalFontSize
        textPaint.textAlign = originalFontAlign
        paint.color = originalColor
        paint.style = originalStyle
    }

    private fun drawScoreAndBombs(canvas: Canvas) {
        val pauseBitmap = if (status == STATUS_GAME_STARTED) bitmaps[9] else bitmaps[10]
        val pauseBitmapDstRecF = getPauseBitmapDstRecF()
        val pauseLeft = pauseBitmapDstRecF.left
        val pauseTop = pauseBitmapDstRecF.top
        canvas.drawBitmap(pauseBitmap, pauseLeft, pauseTop, paint)

        val scoreLeft = pauseLeft + pauseBitmap.width + 20 * density
        val scoreTop = fontSize + pauseTop + pauseBitmap.height / 2 - fontSize / 2
        canvas.drawText("$score", scoreLeft, scoreTop, textPaint)

        if (combatAircraft != null && !combatAircraft!!.isDestroyed()) {
            val bombCount = combatAircraft!!.getBombCount()
            if (bombCount > 0) {
                val bombBitmap = bitmaps[11]
                val bombTop = canvas.height - bombBitmap.height
                canvas.drawBitmap(bombBitmap, 0f, bombTop.toFloat(), paint)

                val bombCountLeft = bombBitmap.width + 10 * density
                val bombCountTop = fontSize + bombTop + bombBitmap.height / 2 - fontSize / 2
                canvas.drawText("X $bombCount", bombCountLeft, bombCountTop, textPaint)
            }
        }
    }

    private fun destroyBulletsFrontOfCombatAircraft() {
        if (combatAircraft != null) {
            val aircraftY = combatAircraft!!.getY()
            val aliveBullets: List<Bullet> = getAliveBullets()
            for (bullet in aliveBullets) {
                if (aircraftY <= bullet.getY()) {
                    bullet.destroy()
                }
            }
        }
    }

    private fun removeDestroyedSprites() {
        val iterator = sprites.iterator()
        while (iterator.hasNext()) {
            val s = iterator.next()
            if (s.isDestroyed()) {
                iterator.remove()
            }
        }
    }

    private fun createRandomSprites(canvasWidth: Int) {
        var sprite: Sprite? = null
        var speed = 2
        var callTime = round((frame / 30).toDouble())
        if (((callTime + 1) % 25).toInt() == 0) {
            if (((callTime + 1) % 50).toInt() == 0) {
                sprite = BombAward(bitmaps[7])
            } else {
                sprite = BulletAward(bitmaps[8])
            }
        } else {
            val nums = arrayOf<Int>(0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2)
            val index = floor(nums.size * Math.random()).toInt()
            val type = nums[index]
            when (type) {
                0 -> sprite = SmallEnemyPlane(bitmaps[4])
                1 -> sprite = MiddleEnemyPlane(bitmaps[5])
                2 -> sprite = BigEnemyPlane(bitmaps[6])
            }
            if (type != 2) {
                if (Math.random() < 0.33) {
                    speed = 4
                }
            }
        }

        if (sprite != null) {
            val spriteWidth = sprite.getWidth()
            val spriteHeight = sprite.getHeight()
            val x = (canvasWidth - spriteWidth) * Math.random()
            val y = -spriteHeight
            sprite.setX(x.toFloat())
            sprite.setY(y.toFloat())
            if (sprite is AutoSprite) {
                sprite.setSpeed(speed.toFloat())
            }
            addSprite(sprite)
        }
    }

    /*-------------------------------touch------------------------------------*/
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchType = resolveTouchType(event!!)
        when (status) {
            STATUS_GAME_STARTED -> {
                when (touchType) {
                    TOUCH_MOVE -> {
                        combatAircraft?.centerTo(touchX, touchY)
                    }
                    TOUCH_DOUBLE_CLICK -> {
                        combatAircraft?.bomb(this)
                    }
                }
            }
            STATUS_GAME_PAUSED -> {
                if (lastSingleClickTime > 0) postInvalidate()
            }
            STATUS_GAME_OVER -> {
                if (lastSingleClickTime > 0) postInvalidate()
            }
        }
        return true
    }

    private fun resolveTouchType(event: MotionEvent): Int {
        var touchType = -1
        val action = event.action
        touchX = event.x
        touchY = event.y
        when (action) {
            MotionEvent.ACTION_MOVE -> {
                val deltaTime = System.currentTimeMillis() - touchDownTime
                if (deltaTime > singleClickDurationTime) {
                    touchType = TOUCH_MOVE
                }
            }
            MotionEvent.ACTION_DOWN -> {
                touchDownTime = System.currentTimeMillis()
            }
            MotionEvent.ACTION_UP -> {
                touchUpTime = System.currentTimeMillis()
                val downUpDurationTime = touchUpTime - touchDownTime
                if (downUpDurationTime <= singleClickDurationTime) {
                    val twoClickDurationTime = touchUpTime - lastSingleClickTime

                    if (twoClickDurationTime <= doubleClickDurationTime) {
                        touchType = TOUCH_DOUBLE_CLICK
                        lastSingleClickTime = -1
                        touchDownTime = -1
                        touchUpTime = -1
                    } else {
                        lastSingleClickTime = touchUpTime
                    }
                }
            }
        }
        return touchType
    }

    private fun isSingleClick(): Boolean {
        var singleClick = false
        if (lastSingleClickTime > 0) {
            val deltaTime: Long = System.currentTimeMillis() - lastSingleClickTime

            if (deltaTime >= doubleClickDurationTime) {
                singleClick = true
                lastSingleClickTime = -1
                touchDownTime = -1
                touchUpTime = -1
            }
        }
        return singleClick
    }

    private fun onSingleClick(x: Float, y: Float) {
        when (status) {
            STATUS_GAME_STARTED -> {
                if (isClickPause(x, y)) pause()
            }
            STATUS_GAME_PAUSED -> {
                if (isClickContinueButton(x, y)) resume()
            }
            STATUS_GAME_OVER -> {
                if (isClickRestartButton(x, y)) restart()
            }
        }
    }

    private fun isClickPause(x: Float, y: Float): Boolean {
        val pauseRecF = getPauseBitmapDstRecF()
        return pauseRecF.contains(x, y)
    }

    private fun isClickContinueButton(x: Float, y: Float): Boolean {
        return continueRect.contains(x.toInt(), (y + 200).toInt())
    }

    private fun isClickRestartButton(x: Float, y: Float): Boolean {
//        return continueRect.contains(x.toInt(), y.toInt())
        return continueRect.contains(x.toInt(), (y + 200).toInt())
    }

    private fun getPauseBitmapDstRecF(): RectF {
        val pauseBitmap = if (status == STATUS_GAME_STARTED) bitmaps[9] else bitmaps[10]
        val rectF = RectF()
        rectF.left = 15 * density
        rectF.top = 15 * density
        rectF.right = rectF.left + pauseBitmap.width
        rectF.bottom = rectF.top + pauseBitmap.height
        return rectF
    }


    /*----- destroy -----*/
    private fun destroyNotRecycleBitmaps() {
        status = STATUS_GAME_DESTROYED
        frame = 0
        score = 0

        if (combatAircraft != null) {
            combatAircraft!!.destroy()
        }
        combatAircraft = null

        for (s in sprites) {
            s.destroy()
        }
        sprites.clear()
    }

    fun destroy() {
        destroyNotRecycleBitmaps()

        for (bitmap in bitmaps) {
            bitmap.recycle()
        }
        bitmaps.clear()
    }


    /*----- public methods -----*/

    fun addSprite(sprite: Sprite) {
        spritesNeedAdded.add(sprite)
    }

    fun addScore(value: Int) {
        score += value
    }

    fun getStatus(): Int = this.status

    fun getDensity(): Float = this.density

    fun getYellowBulletBitmap(): Bitmap = bitmaps[2]
    fun getBlueBulletBitmap(): Bitmap = bitmaps[3]
    fun getExplosionBitmap(): Bitmap = bitmaps[1]
    fun getAliveEnemyPlanes(): List<EnemyPlane> {
        val enemyPlanes = arrayListOf<EnemyPlane>()
        for (s in sprites) {
            if (!s.isDestroyed() && s is EnemyPlane) {
                enemyPlanes.add(s)
            }
        }
        return enemyPlanes
    }

    fun getAliveBombAwards(): List<BombAward> {
        val bombAwards = arrayListOf<BombAward>()
        for (s in sprites) {
            if (!s.isDestroyed() && s is BombAward) {
                bombAwards.add(s)
            }
        }
        return bombAwards
    }

    fun getAliveBulletAwards(): List<BulletAward> {
        val bulletAwards = arrayListOf<BulletAward>()
        for (s in sprites) {
            if (!s.isDestroyed() && s is BulletAward) {
                bulletAwards.add(s)
            }
        }
        return bulletAwards
    }

    fun getAliveBullets(): List<Bullet> {
        val bullets = arrayListOf<Bullet>()
        for (s in sprites) {
            if (!s.isDestroyed() && s is Bullet) {
                bullets.add(s)
            }
        }
        return bullets
    }

    companion object {
        const val STATUS_GAME_STARTED = 1
        const val STATUS_GAME_PAUSED = 2
        const val STATUS_GAME_OVER = 3
        const val STATUS_GAME_DESTROYED = 4

        const val TOUCH_MOVE = 1
        const val TOUCH_SINGLE_CLICK = 2
        const val TOUCH_DOUBLE_CLICK = 3
        const val singleClickDurationTime = 200
        const val doubleClickDurationTime = 300
    }
}