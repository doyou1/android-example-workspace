package com.example.progressbarproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.progressbarproject.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnStart.setOnClickListener {
            if (binding.etCount.text.isNotEmpty()) {
                startLoadingBar()
            } else {
                Toast.makeText(this, "please input value", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMoveToProgressBar.setOnClickListener {
            val intent = Intent(this, ProgressBarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startLoadingBar() {
        binding.progressBar.visibility = View.VISIBLE
        val second = binding.etCount.text.toString().toLong()
        val countDownTimer = LoadingCountDownTimer(second * 1000, 1000, ::hideProgressBar, ::setCountText)
        binding.tvProgress.text = second.toString()
        countDownTimer.start()
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvProgress.text = "0"
    }

    private fun setCountText(second: Int) {
        binding.tvProgress.text = second.toString()
    }

}