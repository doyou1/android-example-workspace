package com.example.qnaproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.ActivityQnaRegisterBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QnaRegisterActivity: AppCompatActivity() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private lateinit var binding: ActivityQnaRegisterBinding
    val MEM_ID = "73"

    // 제한 글자수 변수
    private val titleTextLimit = 10
    private val contentTextLimit = 20

    // 유효성 검사 결과 FLAG
    private val SUCCESS = 0
    private val OVERFLOW_TITLE = 1
    private val NULL_TITLE = 2
    private val OVERFLOW_CONTENT = 3
    private val NULL_CONTENT = 4

    private val tag = "QnaRegisterActivity"


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
                SUCCESS -> registerQna(titleText, contentText)
                OVERFLOW_TITLE -> Toast.makeText(this, "문의 제목의 글자수를 확인해주세요(최대: ${titleTextLimit})", Toast.LENGTH_SHORT).show()
                OVERFLOW_CONTENT -> Toast.makeText(this, "문의 내용의 글자수를 확인해주세요(최대: ${contentTextLimit})", Toast.LENGTH_SHORT).show()
                NULL_TITLE -> Toast.makeText(this, "문의 제목을 작성해주세요(최대: ${titleTextLimit})", Toast.LENGTH_SHORT).show()
                NULL_CONTENT -> Toast.makeText(this, "문의 내용을 작성해주세요(최대: ${contentTextLimit})", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "작성된 텍스트 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     *
     */
    private fun registerQna(titleText: String, contentText: String) {
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val qnaService = retrofit.create(QnaService::class.java)

        val newQna = NewQna(MEM_ID, titleText, contentText)

        val call: Call<ResponseModel> = qnaService.registerNewQna(newQna)

        val mContext = this
        CoroutineScope(Dispatchers.Main).launch {
            async {
                val resultCode:Int = setInterfaceData(call)

                when(resultCode) {
                    // 문의 등록 성공, 문의리스트 화면으로 돌아가 신규 데이터 확인
                    200 -> {
                        Toast.makeText(mContext, "문의 등록 성공!", Toast.LENGTH_SHORT).show()
                        moveToBack()
                    }
                    // 문의 등록 실패, 등록 버튼 비활성화
                    else -> {
                        Toast.makeText(mContext, "문의 등록 실패", Toast.LENGTH_SHORT).show()
                        binding.btnQnaRegister.isEnabled = false
                    }
                }
            }
        }
    }

    /**
     * Set
     */
    suspend fun setInterfaceData(call: Call<ResponseModel>) : Int =
        suspendCancellableCoroutine { continuation ->

        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<ResponseModel> {
            // Response Success
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {

                // {
                //     "code": 200,
                //     "message": "",
                //     "data":[]
                // }

                Log.d(tag, "성공 : ${response.code()}")

                continuation.resume(response.code()) {
                    Log.e(tag, it.toString())
                }
            }
            // Response Fail
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.d(tag, "실패 : $t")
            }
        })
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
    /**
     * Activity to Activity 이동, QnaActivity(QnaList화면으로 이동)
     */
    private fun moveToBack() {
        val intent = Intent(this, QnaActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
}