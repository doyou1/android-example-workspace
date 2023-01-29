package com.example.progressbarproject

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.progressbarproject.databinding.ActivityProgressBarBinding
import kotlin.math.roundToInt

class ProgressBarActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityProgressBarBinding
    private var inputSecond: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_progress_bar)

        // CountDownTimer 실행 (EditText의 값만큼 진행)
        binding.btnStart.setOnClickListener {
            if (binding.etCount.text.isNotEmpty()) {
                startLoadingBar()
            } else {
                Toast.makeText(this, "please input value", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMoveToLoadingBar.setOnClickListener {
            onBackPressed()
        }
    }

    private fun startLoadingBar() {
        inputSecond = binding.etCount.text.toString().toLong()
        val countDownTimer = ProgressCountDownTimer(inputSecond * 1000, 1000, ::reset, ::setCount)
        binding.tvProgress.text = inputSecond.toString()
        binding.progressBar.max = inputSecond.toInt()
        countDownTimer.start()
    }

    private fun reset() {
        binding.tvProgress.text = "0"
        binding.progressBar.progress = 0
    }

    private fun setCount(second: Int) {
        binding.tvProgress.text = second.toString()
        binding.progressBar.progress = (inputSecond - second).toInt()
    }
}