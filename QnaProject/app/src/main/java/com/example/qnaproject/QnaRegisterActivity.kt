package com.example.qnaproject

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.ActivityQnaRegisterBinding
import android.os.Looper

import android.R
import android.app.Service
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager

import android.widget.LinearLayout




class QnaRegisterActivity: AppCompatActivity() {

    private val tag = "QnaRegisterActivity"

    private lateinit var binding: ActivityQnaRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna_register)
        setSupportActionBar(binding.toolbarQnaRegister.root as Toolbar)

        // 상단 BackButton Click시 QnaList로 이동
        binding.toolbarQnaRegister.ibBack.setOnClickListener {
            moveToBack()
        }
    }

    // Android 내장 BackButton 클릭시
    override fun onBackPressed() {
        moveToBack()
    }

    /**
     * Activity to Activity 이동, QnaActivity(QnaList화면으로 이동)
     */
    private fun moveToBack() {
        val intent = Intent(this, QnaActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
}