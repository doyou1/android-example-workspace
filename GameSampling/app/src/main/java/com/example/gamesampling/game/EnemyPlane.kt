package com.example.gamesampling.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

open class EnemyPlane(bitmap: Bitmap) : AutoSprite(bitmap) {

    private var power: Int = 1
    private var value: Int = 0

    fun setPower(power: Int) {
        this.power = power
    }

    fun getPower(): Int {
        return power
    }

    fun setValue(value: Int) {
        this.value = value
    }

    fun getValue(): Int {
        return value
    }

    override fun afterDraw(canvas: Canvas, paint: Paint, gameView: GameView) {
        super.afterDraw(canvas, paint, gameView)

        if (!isDestroyed()) {
            val bullets = gameView.getAliveBullets()
            for (bullet in bullets) {
                val p = getCollidePointWithOther(bullet)
                p?.let {
                    bullet.destroy()
                    power--
                    if (power <= 0) {
                        explode(gameView)
                        return
                    }
                }
            }
        }
    }

    fun explode(gameView: GameView) {
        val centerX = getX() + getWidth() / 2
        val centerY = getY() + getHeight() / 2
        val bitmap = gameView.getExplosionBitmap()
        val explosion = Explosion(bitmap)
        explosion.centerTo(centerX, centerY)
        gameView.addSprite(explosion)

        gameView.addScore(value)
        destroy()
    }
}