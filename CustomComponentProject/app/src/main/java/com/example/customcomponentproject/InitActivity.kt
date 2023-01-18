package com.example.customcomponentproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.customcomponentproject.databinding.ActivityInitBinding

class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnStart.setOnClickListener {
            it.isClickable = false  // prevent duplicate call

            // LoadingBar to ProgressBar
            // hide LoadingBar (id: progress_bar)
            binding.progressBar.visibility = View.INVISIBLE

            // show ProgressBar (id: progress_bar_horizontal)
            binding.progressBarHorizontal.visibility = View.VISIBLE
            binding.tvProgressBarPercent.visibility = View.VISIBLE  // show progress status text

            startProgressBar()
        }
    }

    /**
     * ProgressBar start method with CountDownTimer
     */
    private fun startProgressBar() {
        // millisFuture(Timer timeout seconds), countDownInterval(onTick call interval) millisecond
        // 5000ms, 50 (5seconds, 0.05seconds interval)
        val countDownTimer = ProgressCountDownTimer(5000, 50, ::addCount, ::moveToMain)
        countDownTimer.start()
    }

    /**
     * Called by the onTick method called at regular intervals in ProgressCountDownTimer.
     * Increase the progress by '1' each time called and set the percent text as increased.
     */
    private fun addCount() {
        binding.progressBarHorizontal.progress++
        binding.tvProgressBarPercent.text = "${binding.progressBarHorizontal.progress}%"
    }

    /**
     * Called from onFinish method called when ProgressCountDownTimer ends.
     * Go to MainActivity where the actual content resides and finish the current activity.
     */
    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}