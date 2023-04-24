package com.example.gamesamplingv2.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gamesamplingv2.R
import com.example.gamesamplingv2.databinding.ActivityJumpCharacterBinding

class JumpCharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJumpCharacterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJumpCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bitmapIds = arrayOf(
            R.drawable.character,
//            R.drawable.character2,
//            R.drawable.character3,
//            R.drawable.character4,
        )

        binding.jumpCharacterView.start(bitmapIds)
    }

    override fun onPause() {
        super.onPause()
        binding.jumpCharacterView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.jumpCharacterView.destroy()
    }
}