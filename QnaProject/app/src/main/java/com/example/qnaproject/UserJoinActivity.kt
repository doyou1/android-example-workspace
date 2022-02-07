package com.example.qnaproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.ActivityUserJoinBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserJoinActivity: AppCompatActivity() {

    private val tag = "UserJoinActivity"
    private val baseUrl = "https://api.jamjami.co.kr/"

    private lateinit var binding:ActivityUserJoinBinding
    private lateinit var mContext:Context
    private var mLastContentHeight = 0

    // Nickname Text 글자 제한수
    private val nickNameTextLimit = 10

    // 유효성 검사 결과 FLAG
    private val SUCCESS = 0
    private val OVERFLOW_NICKNAME = 1
    private val NULL_NICKNAME = 2

    private lateinit var MEM_SNS_TYPE: String
    private lateinit var MEM_SNS_ID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState,)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_join)
        setSupportActionBar(binding.toolbarUserJoin.root as Toolbar)
        mContext = this
        mLastContentHeight = this.findViewById<View>(Window.ID_ANDROID_CONTENT).getHeight();

        getDataFromPreviousActivity()
        setClickEvent()
        setTouchEvent(binding.root)

    }

    private fun getDataFromPreviousActivity() {
        // QnaList 화면로부터의 QNA_ID 전송 확인
        MEM_SNS_TYPE = intent.getStringExtra("MEM_SNS_TYPE").toString()
        MEM_SNS_ID = intent.getStringExtra("MEM_SNS_ID").toString()
    }

    private fun setClickEvent() {
        binding.toolbarUserJoin.ibBack.setOnClickListener { // 이전 화면으로 전환, StartActivity nonFinish
            super.onBackPressed()
        }

        binding.btnQnaRegister.setOnClickListener{
            val nickNameText = binding.etMemNick.text.toString()
            val telText = binding.etMemTel.text.toString()

            // 유효성 검사, 결과에 따라 Toast 메시지 출력 및 문의 리스트화면 이동
            when (validateText(nickNameText)) {
                SUCCESS -> joinUser(nickNameText, telText)
                OVERFLOW_NICKNAME-> Toast.makeText(
                    this,
                    "닉네임의 글자수를 확인해주세요(최대: ${nickNameTextLimit})",
                    Toast.LENGTH_SHORT
                ).show()
                NULL_NICKNAME -> Toast.makeText(
                    this,
                    "닉네임을 입력해주세요(최대: ${nickNameTextLimit})",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Toast.makeText(this, "작성된 텍스트 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
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

    private fun joinUser(nickNameText: String, telText: String) {
    // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val userService = retrofit.create(UserService::class.java)
        val user = User(MEM_SNS_TYPE, MEM_SNS_ID, nickNameText, telText, null)
        val call: Call<UserResponseModel> = userService.joinUser(user)
        call.enqueue(object : Callback<UserResponseModel> {
            // Response Success
            override fun onResponse(
                call: Call<UserResponseModel>,
                response: Response<UserResponseModel>
            ) {
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as UserResponseModel
                val MEM_ID = resBody.data?.get(0)?.MEM_ID
                resultProcess(MEM_ID, resBody.code)
            }

            // Response Fail
            override fun onFailure(call: Call<UserResponseModel>, t: Throwable) {
                Log.d(tag, "실패 : $t")
            }
        })
    }

    private fun validateText(nickNameText: String): Int {

        if (nickNameText.length > nickNameTextLimit) return OVERFLOW_NICKNAME
        else if (nickNameText.isEmpty() || nickNameText == "" || nickNameText == null) return NULL_NICKNAME

        return SUCCESS
    }

    private fun resultProcess(MEM_ID: Int?, responseCode: Int) {
        when (responseCode) {
            200 -> {
                if (MEM_ID != null) {
                    moveToQnaList(MEM_ID)
                    Toast.makeText(this, "회원가입 성공!.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "적절하지 못한 접근입니다.", Toast.LENGTH_SHORT).show()
                }

            }
            else -> {
                Toast.makeText(this, "적절하지 못한 접근입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun moveToQnaList(MEM_ID: Int) {
        val intent = Intent(this, QnaActivity::class.java)
        intent.putExtra("MEM_ID", MEM_ID)

        this.startActivity(intent)
        this.finish()
    }


    private fun hideSoftKeyboard(mContext: Context) {
        // InputMethodManager 객체 생성
        val inputMethodManager = mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)    // 키보드 숨김
    }


}