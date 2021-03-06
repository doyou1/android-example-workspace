package com.example.qnaproject.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.ActivityQnaRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.qnaproject.domain.NewQna
import com.example.qnaproject.responseModel.NewQnaResponseModel
import com.example.qnaproject.service.QnaService
import com.example.qnaproject.R


/**
 * 신규문의를 등록하는 화면
 * from QnaActivity
 */
class QnaRegisterActivity : AppCompatActivity() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private var MEM_ID = -1

    private lateinit var binding: ActivityQnaRegisterBinding
    private lateinit var mContext: Context
    // 제한 글자수 변수
    private val titleTextLimit = 10
    private val contentTextLimit = 20

    // 유효성 검사 결과 FLAG
    private val SUCCESS = 0
    private val OVERFLOW_TITLE = 1
    private val NULL_TITLE = 2
    private val OVERFLOW_CONTENT = 3
    private val NULL_CONTENT = 4

    // 레이아웃의 높이를 담는 변수 (키보드 visible/hide를 구분하기 위함)
    private var mLastContentHeight = 0

    private val tag = "QnaRegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna_register)
        setSupportActionBar(binding.toolbarQnaRegister.root as Toolbar)
        mContext = this
        getSharedPreferenceData()
        setClickEvent()
<<<<<<< HEAD

        // 키보드에 의한 레이아웃 축소 확인용 메서드
        mLastContentHeight = this.findViewById<View>(Window.ID_ANDROID_CONTENT).getHeight();
        setTouchEvent(binding.root)
    }

    /**
     * SharedPreference 접근 및 저장된 MEM_ID 설정
     */
=======
    }

>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
    private fun getSharedPreferenceData() {
        val sharedPref = this.getSharedPreferences("App", Context.MODE_PRIVATE)
        MEM_ID = sharedPref.getInt("MEM_ID", -1)

        Log.e(tag, "MEM_ID: ${MEM_ID}")

        if (MEM_ID == -1) {    // 기존의 MEM_ID가 없다면..
            Toast.makeText(this, "적절하지 않은 접근입니다.", Toast.LENGTH_SHORT).show()
            moveToMain()
        }
    }

    /**
     * ClickEvent 설정
     */
    private fun setClickEvent() {
        // 상단 BackButton Click시 QnaList로 이동
        binding.toolbarQnaRegister.ibBack.setOnClickListener {
            super.onBackPressed()
        }

        // `등록`버튼 Click Event
        binding.btnQnaRegister.setOnClickListener {
            val titleText = binding.etQnaTitle.text.toString()
            val contentText = binding.etQanContent.text.toString()

            // 유효성 검사, 결과에 따라 Toast 메시지 출력 및 문의 리스트화면 이동
            when (validateText(titleText, contentText)) {
                SUCCESS -> registerQna(titleText, contentText)
                OVERFLOW_TITLE -> Toast.makeText(
                    this,
                    "문의 제목의 글자수를 확인해주세요(최대: ${titleTextLimit})",
                    Toast.LENGTH_SHORT
                ).show()
                OVERFLOW_CONTENT -> Toast.makeText(
                    this,
                    "문의 내용의 글자수를 확인해주세요(최대: ${contentTextLimit})",
                    Toast.LENGTH_SHORT
                ).show()
                NULL_TITLE -> Toast.makeText(
                    this,
                    "문의 제목을 작성해주세요(최대: ${titleTextLimit})",
                    Toast.LENGTH_SHORT
                ).show()
                NULL_CONTENT -> Toast.makeText(
                    this,
                    "문의 내용을 작성해주세요(최대: ${contentTextLimit})",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Toast.makeText(this, "작성된 텍스트 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
<<<<<<< HEAD
=======

        // 키보드에 의한 레이아웃 축소 확인용 메서드
        mLastContentHeight = this.findViewById<View>(Window.ID_ANDROID_CONTENT).getHeight();
        setTouchEvent(binding.root)

>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
    }

    /**
     * EditText가 아닌 부분을 터치했을 경우
     * 키보드를 숨기기 위한 TouchEvent 설정
     */
    private fun setTouchEvent(view: View) {
        if (view !is EditText) {    // 현재 focus View가 EditText가 아니라면, if문 통과하는 구조 공부 필요
            view.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                    hideSoftKeyboard(mContext); // Keyboard를 숨김
                    return false;
                }
            })
        }
    }

    /**
     * 키보드를 숨기는(닫는) 메서드
     */
    private fun hideSoftKeyboard(mContext: Context) {
        // InputMethodManager 객체 생성
        val inputMethodManager = mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)    // 키보드 숨김
    }

    /**
     * 신규문의 등록
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
        val call: Call<NewQnaResponseModel> = qnaService.registerNewQna(newQna)

        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<NewQnaResponseModel> {
            // Response Success
            override fun onResponse(
                call: Call<NewQnaResponseModel>,
                response: Response<NewQnaResponseModel>
            ) {
                // {
                //     "code": 200,
                //     "message": "",
                //     "data":[]
                // }
                Log.d(tag, "성공 : ${response.code()}")

                resultProcess(response.code())
            }

            // Response Fail
            override fun onFailure(call: Call<NewQnaResponseModel>, t: Throwable) {
                Log.d(tag, "실패 : $t")
            }
        })
    }

    /**
     * 문의 작성 유효성 검사
     */
    private fun validateText(titleText: String, contentText: String): Int {
        if (titleText.length > titleTextLimit) return OVERFLOW_TITLE
        else if (contentText.length > contentTextLimit) return OVERFLOW_CONTENT
        else if (titleText.isEmpty() || titleText == "" || titleText == null) return NULL_TITLE
        else if (contentText.isEmpty() || contentText == "" || contentText == null) return NULL_CONTENT

        return SUCCESS
    }

    /**
     * 서버로부터의 응답결과, 코드에 따른 이후 절차 함수
     * responseCode: 200    - 문의 등록 성공 (등록 성공 메세지 출력 및 문의리스트 화면으로 이동)
     * else                 - 문의 등록 실패 (등록 실패 메세지 출력 및 등록 버튼 비활성화)
     */
    private fun resultProcess(responseCode: Int) {
        when (responseCode) {
            // 문의 등록 성공, 문의리스트 화면으로 돌아가 신규 데이터 확인
            200 -> {
                // 키보드 열린 상태에서 등록 시, Toast Message가 화면 중간에 출력되는 문제발생
                // *여전히 발생 - 추후 변경*
                hideSoftKeyboard(this)
                Toast.makeText(this, "문의 등록 성공!", Toast.LENGTH_SHORT).show()
//                super.onBackPressed() // 되돌아갈 경우 List Refresh X
                moveToQnaList()
            }
            // 문의 등록 실패, 등록 버튼 비활성화, 정확하게 다양한 경우는 추후에 추가
            else -> {
                hideSoftKeyboard(this)
                Toast.makeText(this, "문의 등록 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Activity to Activity 이동, MainActivity
     */

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }

    /**
     * Activity to Activity 이동, QnaActivity
     */
    private fun moveToQnaList() {
        val intent = Intent(this, QnaActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }

}