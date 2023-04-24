package com.example.gamesamplingv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gamesamplingv2.activity.AutoBackgroundActivity
import com.example.gamesamplingv2.activity.JumpCharacterActivity
import com.example.gamesamplingv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnJumpCharacter.setOnClickListener {
            val intent = Intent(this, JumpCharacterActivity::class.java)
            startActivity(intent)
        }

        binding.btnAutoBackground.setOnClickListener {
            val intent = Intent(this, AutoBackgroundActivity::class.java)
            startActivity(intent)
        }
    }
}