package com.example.gamesampling.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class CombatAircraft(bitmap: Bitmap) : Sprite(bitmap) {
    private var collide: Boolean = false
    private var bombAwardCount = 0

    private var single: Boolean = true
    private var doubleTime: Int = 0
    private var maxDoubleTime: Int = 140

    private var beginFlushFrame: Long = 0
    private var flushTime: Int = 0
    private var flushFrequency: Int = 16
    private var maxFlushTime: Int = 10
    override fun beforeDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
        super.beforeDraw(canvas, paint, gameView)
        if (!isDestroyed()) {
            validatePosition(canvas)
            if (getFrame() % 7 == 0) {
                fight(gameView)
            }
        }
    }

    private fun validatePosition(canvas: Canvas) {
        if (getX() < 0) {
            setX(0f)
        }
        if (getY() < 0) {
            setY(0f)
        }
        val rectF = getRectF()
        val canvasWidth = canvas.width
        if (rectF.right > canvasWidth) {
            setX((canvasWidth - getWidth()))
        }
        val canvasHeight = canvas.height
        if (rectF.bottom > canvasHeight) {
            setY((canvasHeight - getHeight()).toFloat())
        }
    }

    fun fight(gameView: GameView) {
        if (collide || isDestroyed()) return

        val x = getX() + getWidth() / 2
        val y = getY() - 5
        if (single) {
            val yellowBulletBitmap = gameView.getYellowBulletBitmap()
            val yellowBullet = Bullet(yellowBulletBitmap)
            yellowBullet.moveTo(x, y)
            gameView.addSprite(yellowBullet)
        } else {
            val offset: Float = getWidth() / 4
            val leftX = x - offset
            val rightX = x + offset
            val blueBulletBitmap = gameView.getBlueBulletBitmap()

            val leftBlueBullet = Bullet(blueBulletBitmap)
            leftBlueBullet.moveTo(leftX, y)
            gameView.addSprite(leftBlueBullet)

            val rightBlueBullet = Bullet(blueBulletBitmap)
            rightBlueBullet.moveTo(rightX, y)
            gameView.addSprite(rightBlueBullet)

            doubleTime++
            if (doubleTime >= maxDoubleTime) {
                single = true
                doubleTime = 0
            }
        }
    }

    override fun afterDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
        if (isDestroyed()) return

        if (!collide) {
            val enemyPlanes = gameView.getAliveEnemyPlanes()
            for (enemyPlane in enemyPlanes) {
                if (getCollidePointWithOther(enemyPlane) != null) {
                    explode(gameView)
                    break
                }
            }
        }

        if (beginFlushFrame > 0) {
            val frame: Int = getFrame()
            if (frame >= beginFlushFrame) {
                if ((frame - beginFlushFrame) % flushFrequency == 0L) {
                    setVisibility(!getVisibility())
                    flushTime++
                    if (flushTime >= maxFlushTime) {
                        destroy()
                    }
                }
            }
        }

        if (!collide) {
            for (bombAward in gameView.getAliveBombAwards()) {
                getCollidePointWithOther(bombAward)?.let {
                    bombAwardCount++
                    bombAward.destroy()
                }
            }
            for (bulletAward in gameView.getAliveBulletAwards()) {
                getCollidePointWithOther(bulletAward)?.let {
                    bulletAward.destroy()
                    single = false
                    doubleTime = 0
                }
            }
        }
    }

    private fun explode(gameView: GameView) {
        if (!collide) {
            collide = true
            setVisibility(false)
            val centerX: Float = getX() + getWidth() / 2
            val centerY: Float = getY() + getHeight() / 2

            val explosion = Explosion(gameView.getExplosionBitmap())
            explosion.centerTo(centerX, centerY)
            gameView.addSprite(explosion)
            beginFlushFrame = (getFrame() + explosion.getExplodeDurationFrame()).toLong()
        }
    }

    fun getBombCount(): Int = this.bombAwardCount

    fun bomb(gameView: GameView) {
        if (collide || isDestroyed()) return
        if (bombAwardCount > 0) {
            for (enemyPlane in gameView.getAliveEnemyPlanes()) {
                enemyPlane.explode(gameView)
            }
            bombAwardCount--
        }
    }

    fun isCollide(): Boolean = this.collide

    fun setNotCollide() {
        collide = false
    }
}