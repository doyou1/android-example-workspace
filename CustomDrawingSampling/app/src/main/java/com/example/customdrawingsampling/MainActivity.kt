package com.example.customdrawingsampling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customdrawingsampling.databinding.ActivityMainBinding
import com.example.customdrawingsampling.ladder.InitLadderActivity
import com.example.customdrawingsampling.roulette.RouletteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    var users = mutableListOf<String>()
    var goals = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEVent()
    }


    private fun setClickEVent() {

        binding.btnLadder.setOnClickListener {
            val intent = Intent(this, InitLadderActivity::class.java)
            startActivity(intent)
        }

        binding.btnRoulette.setOnClickListener {
            val intent = Intent(this, RouletteActivity::class.java)
            startActivity(intent)
        }


    }
}