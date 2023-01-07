package com.example.customdrawingsampling

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.customdrawingsampling.databinding.ActivityLadderBinding
import com.example.customdrawingsampling.databinding.ActivityMainBinding

class LadderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLadderBinding
    private val TAG = this::class.java.simpleName
    private val ladderWidth = 20
    private val regSize = 5
    private val regHeight = 5
    private var isMakeReg = false

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
                binding.ladderView.init(ladderSize, ladderWidth, users, goals)
                setRvResult(users)
            }
        }

        binding.btnMakeReg.setOnClickListener {
            isMakeReg = true
            binding.ladderView.makeRegs(regSize, regHeight)
        }

        binding.btnShowAllResult.setOnClickListener {
            if (isMakeReg) {
                binding.ladderView.showAllResult()
            }
        }
    }

    private fun setRvResult(users: Array<String>) {
        setLayoutManager(users.size)
        binding.rvResult.adapter = RvResultAdapter(users.toList(), this)
    }

    fun showResult(position: Int) {
        binding.ladderView.showResult(position)
    }

    private fun setLayoutManager(count: Int) {
        val userLayoutManager = GridLayoutManager(this, count)
        userLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.rvResult.layoutManager = userLayoutManager
    }
}