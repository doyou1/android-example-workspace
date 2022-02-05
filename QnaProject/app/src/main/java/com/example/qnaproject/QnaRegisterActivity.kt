package com.example.qnaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.ActivityQnaRegisterBinding

class QnaRegisterActivity: AppCompatActivity() {

    private val tag = "QnaRegisterActivity"

    private lateinit var binding: ActivityQnaRegisterBinding

    private val titleTextLimit = 10
    private val contentTextLimit = 20

    private val SUCCESS = 0
    private val OVERFLOW_TITLE = 1
    private val NULL_TITLE = 2
    private val OVERFLOW_CONTENT = 3
    private val NULL_CONTENT = 4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna_register)
        setSupportActionBar(binding.toolbarQnaRegister.root as Toolbar)

        // 상단 BackButton Click시 QnaList로 이동
        binding.toolbarQnaRegister.ibBack.setOnClickListener {
            moveToBack()
        }

        // 등록Btn Click Event
        binding.btnQnaRegister.setOnClickListener {
            val titleText = binding.etQnaTitle.text.toString()
            val contentText = binding.etQanContent.text.toString()

            // 유효성 검사, 결과에 따라 Toast 메시지 출력 및 문의 리스트화면 이동
            when (validateText(titleText, contentText)) {
                SUCCESS -> moveToBack()
                OVERFLOW_TITLE -> Toast.makeText(this, "문의 제목의 글자수를 확인해주세요(최대: ${titleTextLimit})", Toast.LENGTH_SHORT).show()
                OVERFLOW_CONTENT -> Toast.makeText(this, "문의 내용의 글자수를 확인해주세요(최대: ${contentTextLimit})", Toast.LENGTH_SHORT).show()
                NULL_TITLE -> Toast.makeText(this, "문의 제목을 작성해주세요(최대: ${titleTextLimit})", Toast.LENGTH_SHORT).show()
                NULL_CONTENT -> Toast.makeText(this, "문의 내용을 작성해주세요(최대: ${contentTextLimit})", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "작성된 텍스트 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /**
     * 문의 작성 유효성 검사
     */
    private fun validateText(titleText: String, contentText: String) : Int {
        if (titleText.length > titleTextLimit) return OVERFLOW_TITLE
        else if (contentText.length > contentTextLimit) return OVERFLOW_CONTENT
        else if (titleText.isEmpty() || titleText == "" || titleText == null) return NULL_TITLE
        else if (contentText.isEmpty() || contentText == "" || contentText == null) return NULL_CONTENT

        return SUCCESS
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