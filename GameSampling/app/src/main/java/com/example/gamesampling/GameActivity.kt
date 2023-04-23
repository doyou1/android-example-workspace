package com.example.gamesampling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamesampling.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val bitmapIds = arrayOf(
            R.drawable.plane,
            R.drawable.explosion,
            R.drawable.yellow_bullet,
            R.drawable.blue_bullet,
            R.drawable.small,
            R.drawable.middle,
            R.drawable.big,
            R.drawable.bomb_award,
            R.drawable.bullet_award,
            R.drawable.pause1,
            R.drawable.pause2,
            R.drawable.bomb
        )
        binding.gameView.start(bitmapIds)
    }

    override fun onPause() {
        super.onPause()
        binding.gameView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.gameView.destroy()
    }
}