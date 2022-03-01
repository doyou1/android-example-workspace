package com.example.customcomponentproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.customcomponentproject.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var resultValue = -1
    private var prevLimit= -1
    private var endLimit = 31

    private var life = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initialize()
        setSeekBarChangeEvent()
        setClickEvent()
    }

    private fun initialize() {
        initProgressTextView()
        getRandomResult()
    }

    /**
     * ProgressBar의 기본 progress 값에 따라
     * 관련 텍스트뷰 초기화
     */
    private fun initProgressTextView() {
        binding.tvProgress.text = binding.seekBar.progress.toString()
        binding.tvPrev.text = "0"
        binding.tvEnd.text = "30"
    }

    /**
     * 업다운게임의 정답 랜덤값 생성
     */
    private fun getRandomResult() {
        resultValue = Random.nextInt(0, 30)
        Log.e(TAG, "resultValue: $resultValue")
        binding.tvResult.text = resultValue.toString()  // 원활한 디버그를 위해 화면에 출력
    }

    /**
     * SeekBar(시크바) 드래그 및 클릭에 따른 변화 리스너 설정
     */
    private fun setSeekBarChangeEvent() {
        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.e(TAG, "onProgressChanged: $progress")
                binding.tvProgress.text = progress.toString()   // 변화한 progress 출력

                // 이미 틀린 걸로 간주하는 범위는 접근하지 않도록 설정
                if (progress <= prevLimit) {
                    seekBar?.progress = prevLimit + 1   // progress 변경시 onProgressChanged 재호출
                } else if (progress >= endLimit) {
                    seekBar?.progress = endLimit - 1
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.e(TAG, "onStartTrackingTouch: $seekBar")

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.e(TAG, "onStopTrackingTouch: $seekBar")
            }
        })
    }

    private fun setClickEvent() {
        // 추측한 값 확인
        binding.btnCheck.setOnClickListener {
            reduceLife()    // Life를 줄이고

            val current = binding.seekBar.progress

            // 현재 값 비교
            if (resultValue > current) {    // 정답이 더 클 경우..
                Toast.makeText(this, "Up!", Toast.LENGTH_LONG).show()
                prevLimit = current // 최저제한이 현재 값이 됨
                binding.seekBar.progress++  // 한칸 뒤로 이동
            }
            else if (resultValue < current) { // 정답이 더 작은 경우..
                Toast.makeText(this, "Down!", Toast.LENGTH_LONG).show()
                endLimit = current  // 최고제한이 현재 값이 됨
                binding.seekBar.progress--  // 한칸 앞으로 이동
            }
            else {  // 둘다 아니라면 `정답`
                Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show()
                // 재도전 버튼 show, 확인 버튼 click disable
                binding.btnRetry.visibility = View.VISIBLE
                binding.btnCheck.isClickable = false

                return@setOnClickListener
            }

            if (life == -1) {   // 3개의 life를 모두 잃고, 못 맞췄다면
                Toast.makeText(this, "Fail!", Toast.LENGTH_LONG).show()
                binding.btnRetry.visibility = View.VISIBLE
                binding.btnCheck.isClickable = false
            }
        }

        // 숨김 기능 스위치 클릭 메서드
        binding.swiVisibility.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding.layoutCheckBox.visibility = View.INVISIBLE
                binding.layoutContent.visibility = View.INVISIBLE
            } else {
                binding.layoutCheckBox.visibility = View.VISIBLE
                binding.layoutContent.visibility = View.VISIBLE
            }
        }

        // 재도전 버튼 클릭시
        binding.btnRetry.setOnClickListener {
            // InitActivity로 이동
            val intent = Intent(this, InitActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * life 감소 메서드
     * life 감소는 Checkbox의 checked로 표현
     */
    private fun reduceLife() {
        life--

        if (life == 2) {
            binding.checkBox1.isChecked = false
            binding.checkBox2.isChecked = true
            binding.checkBox3.isChecked = true
        } else if (life == 1) {
            binding.checkBox1.isChecked = false
            binding.checkBox2.isChecked = false
            binding.checkBox3.isChecked = true
        } else if (life == 0) {
            binding.checkBox1.isChecked = false
            binding.checkBox2.isChecked = false
            binding.checkBox3.isChecked = false
        }
    }
}