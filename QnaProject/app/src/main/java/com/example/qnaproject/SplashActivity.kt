package com.example.qnaproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.ActivitySplashBinding
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity: AppCompatActivity() {

    private val baseUrl = "https://api.jamjami.co.kr/"

    private val tag = "StartActivity"

    private lateinit var binding: ActivitySplashBinding
    private lateinit var mContext:Context
    private lateinit var mKakaoApi: UserApiClient
    private val MEM_SNS_TYPE = "K"
    private lateinit var MEM_SNS_ID:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, "c4ab14465de77a0d0621a4f8f587bd5b")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        mContext = this
        mKakaoApi = UserApiClient.instance
        binding.btnKakaoLogin.setOnClickListener {
            getAccessToken(mContext)
        }

    }

    private fun getAccessToken(mContext:Context) {
        mKakaoApi.loginWithKakaoAccount(mContext) { token, error ->
            if (error != null) {
                Log.e(tag, "getAccessToken 실패", error)
            }
            else if (token != null) {
                Log.e(tag, "getAccessToken 성공! ${token.accessToken}")

                setKakaoUserId()
            }
        }
    }

    private fun setKakaoUserId() {
        mKakaoApi.me { user, error ->
            if (error != null) {
                Log.e(tag, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(tag, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                MEM_SNS_ID = user.id.toString()
                kakaoLogin()
            }
        }
    }

    private fun kakaoLogin() {
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val userService = retrofit.create(UserService::class.java)

        val call: Call<ResponseModel> = userService.socialLogin(MEM_SNS_TYPE, MEM_SNS_ID)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) { // Response Success
                Log.d(tag, "성공 : ${response}")
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as ResponseModel
                Log.d(tag, "성공 : ${resBody}")
                Log.d(tag, "성공 : ${resBody.code}")

                resultProcess(resBody.code)
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {   // Response Fail
                Log.d(tag, "실패 : $t")
            }
        })
    }

    /**
     * 서버로부터의 응답결과, 코드에 따른 이후 절차 함수
     * responseCode: 423    - 가입된 유저 없음 (회원가입 화면으로 이동)
     * responseCode: 200    - 가입된 유저 있음 (문의리스트 화면으로 이동)
     * else                 -
     */
    private fun resultProcess(responseCode: Int) {
        when (responseCode) {
            423 -> {
                moveToUserJoin()
            }
            200 -> {
                moveToQnaList()
            }
            else -> {
                Toast.makeText(this, "적절하지 못한 접근입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun moveToUserJoin() {
        val intent = Intent(this, UserJoinActivity::class.java)
        intent.putExtra("MEM_SNS_TYPE", MEM_SNS_TYPE)
        intent.putExtra("MEM_SNS_ID", MEM_SNS_ID)

        this.startActivity(intent)
//        this.finish() // onBackPressed 동작을 위한 이전 액티비티 not finish
    }

    private fun moveToQnaList() {
        val intent = Intent(this, QnaActivity::class.java)
        intent.putExtra("MEM_SNS_TYPE", MEM_SNS_TYPE)
        intent.putExtra("MEM_SNS_ID", MEM_SNS_ID)
        this.startActivity(intent)
        this.finish() // 재로그인은 실시할 필요가 없으므로

    }
}

