package com.example.customdrawingsampling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.customdrawingsampling.databinding.ActivityLadderBinding
import com.example.customdrawingsampling.databinding.ActivityMainBinding

class LadderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLadderBinding
    private val TAG = this::class.java.simpleName
    private val ladderWidth = 20
    private val regSize = 5
    private val regHeight = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLadderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val ladderSize = intent.getIntExtra(TEXT_COUNT, 2)
        val users = intent.getStringArrayExtra(TEXT_USER)
        val goals = intent.getStringArrayExtra(TEXT_GOAL)

        binding.ladderView.post {
            if (users != null && goals != null) {
                binding.ladderView.init(ladderSize, ladderWidth, regSize, regHeight, users, goals)
            }
        }
    }
}