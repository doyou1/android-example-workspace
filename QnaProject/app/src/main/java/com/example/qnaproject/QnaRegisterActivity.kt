package com.example.qnaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.ActivityQnaRegisterBinding

class QnaRegisterActivity: AppCompatActivity() {

    private val tag = "QnaRegisterActivity"

    private lateinit var binding: ActivityQnaRegisterBinding

    private val titleTextLimit = 10
    private val contentTextLimit = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna_register)
        setSupportActionBar(binding.toolbarQnaRegister.root as Toolbar)

        // 상단 BackButton Click시 QnaList로 이동
        binding.toolbarQnaRegister.ibBack.setOnClickListener {
            moveToBack()
        }

        // 등록 Button Click Event
        binding.btnQnaRegister.setOnClickListener {
            val titleText = binding.etQnaTitle.text.toString()
            val contentText = binding.etQanContent.text.toString()

            if (validateText(titleText, contentText)) {
                Log.e(tag, "Validation Success ${titleText}, ${contentText}")
            } else {
                Log.e(tag, "Validation Fail")
            }
        }

    }

    /**
     * 문의 작성 유효성 검사
     */
    private fun validateText(titleText: String, contentText: String) : Boolean {

        return !(titleText.length > titleTextLimit
                || contentText.length > contentTextLimit)

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