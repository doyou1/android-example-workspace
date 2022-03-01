package com.example.customcomponentproject

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customcomponentproject.databinding.ActivityInitBinding

class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_init)

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnStart.setOnClickListener {   // 시작버튼 클릭시
            it.isClickable = false  // 중복 호출 방지

            // LoadingBar to ProgressBar
            // hide LoadingBar (id: progress_bar)
            binding.progressBar.visibility = View.INVISIBLE

            // show ProgressBar (id: progress_bar_horizontal)
            binding.progressBarHorizontal.visibility = View.VISIBLE
            binding.tvProgressBarPercent.visibility = View.VISIBLE  // 진행률 텍스트 함께 show

            startProgressBar()
        }
    }

    /**
     * CountDownTimer와 함께 ProgressBar 자동 진행 메서드
     */
    private fun startProgressBar() {
        // CountDownTimer는 millisFuture(타이머 제한 초), countDownInterval(onTick 호출간격)을 millisecond로 받는다.
        // 5000, 500 (5초, 0.5초 간격)
        val countDownTimer = ProgressCountDownTimer(5000, 500, ::addCount, ::moveToMain)    
        countDownTimer.start()
    }

    /**
     * ProgressCountDownTimer의
     * 일정 간격마다 호출되는 onTick 메서드에서 호출됨
     * 호출될때마다 progress를 `1`씩 증가시키고
     * 증가된만큼 percent 텍스트 설정
     */
    private fun addCount() {
        binding.progressBarHorizontal.progress++
        binding.tvProgressBarPercent.text = "${binding.progressBarHorizontal.progress * 10}%"
    }

    /**
     * ProgressCountDownTimer의
     * 타이머가 끝났을 때 호출되는 onFinish 메서드에서 호출됨
     * 실제 컨텐츠가 있는 MainActivity로 이동하고
     * 현재 액티비티 소멸
     */
    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}

class ProgressCountDownTimer(private val millisFuture: Long, private val countDownInterval: Long, val addCount: () -> Unit, val moveToMain: () -> Unit) : CountDownTimer(millisFuture, countDownInterval) {
    private val TAG = "MyCountDownTimer"

    // `countDownInterval` ms마다 호출됨
    override fun onTick(millisUntilFinished: Long) {
        addCount()
    }

    override fun onFinish() {
        Log.e(TAG, "onFinish")
        moveToMain()
    }
}