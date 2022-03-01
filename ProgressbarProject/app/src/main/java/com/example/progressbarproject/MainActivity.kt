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
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // CountDownTimer 실행 (EditText의 값만큼 진행)
        binding.btnStart.setOnClickListener {
            if (binding.etCount.text.isNotEmpty()) {
                startLoadingBar()
            } else {
                Toast.makeText(this, "값을 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMoveToProgressBar.setOnClickListener {
            val intent = Intent(this, ProgressBarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startLoadingBar() {
        binding.progressBar.visibility = View.VISIBLE           // 타이머 시작시 원형 로딩바 쇼잉
        val second = binding.etCount.text.toString().toLong()   // EditText값을 타이머의 second(초)로 설정, inputType=number
        // CountDownTimer는 millisFuture(타이머 제한 초), countDownInterval(onTick 호출간격)을 millisecond로 받는다. -> second * 1000
        val countDownTimer = LoadingCountDownTimer(second * 1000, 1000, ::hideProgressBar, ::setCountText)
        binding.tvProgress.text = second.toString()
        countDownTimer.start()
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvProgress.text = ""
    }

    private fun setCountText(second: Int) {
        binding.tvProgress.text = second.toString()
    }

}

class LoadingCountDownTimer(millisFuture: Long, private val countDownInterval: Long, val hideProgressBar: () -> Unit, val setCountText: (Int) -> Unit) : CountDownTimer(millisFuture, countDownInterval) {
    private val TAG = "MyCountDownTimer"

    override fun onTick(millisUntilFinished: Long) {
        // 남은 millisecond를 파라미터로 제공해준다.
        // (millisUntilFinished / countDownInterval)을 통해 남은 `초`를 계산한다
        val second = (millisUntilFinished / countDownInterval).toDouble().roundToInt()
        setCountText(second)
    }
    override fun onFinish() {
        Log.e(TAG, "onFinish")
        hideProgressBar()
    }
}