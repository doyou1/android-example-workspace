package com.example.customcomponentproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.customcomponentproject.databinding.ActivityInitBinding
import com.example.customcomponentproject.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private var resultValue = -1
    private var prevLimit = -1
    private var endLimit = 31

    private var life = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initProgressTextView()
        getRandomResult()
    }

    override fun onResume() {
        super.onResume()
        setSeekBarChangeEvent()
        setClickEvent()
    }

    private fun initProgressTextView() {
        binding.tvProgress.text = binding.seekBar.progress.toString()
        binding.tvPrev.text = "0"
        binding.tvEnd.text = "30"
    }

    private fun getRandomResult() {
        resultValue = Random.nextInt(0, 30)
        Log.e(TAG, "resultValue: $resultValue")
        // for debug
        binding.tvResult.text = resultValue.toString()
    }

    private fun setSeekBarChangeEvent() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.e(TAG, "onProgressChanged: $progress")
                binding.tvProgress.text = progress.toString()

                if (progress <= prevLimit) {
                    // seekBar.progress change -> call again onProgressChanged
                    seekBar?.progress = prevLimit + 1
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

        binding.btnCheck.setOnClickListener {
            reduceLife()
            val current = binding.seekBar.progress

            if (resultValue > current) {    //  above result
                Toast.makeText(this, "Up!", Toast.LENGTH_LONG).show()
                prevLimit = current
                binding.seekBar.progress++
            } else if (resultValue < current) { // down result
                Toast.makeText(this, "Down!", Toast.LENGTH_LONG).show()
                endLimit = current
                binding.seekBar.progress--
            } else {  // correct!
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