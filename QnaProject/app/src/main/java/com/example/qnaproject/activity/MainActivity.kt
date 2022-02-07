package com.example.qnaproject.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.R
import com.example.qnaproject.databinding.ActivityMainBinding
import com.example.qnaproject.responseModel.SocialLoginResponseModel
import com.example.qnaproject.service.UserService
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity:AppCompatActivity() {
    private val baseUrl = "https://api.jamjami.co.kr/"

    private val tag = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var mContext: Context
    private lateinit var mKakaoApi: UserApiClient
    private val MEM_SNS_TYPE = "K"
    private lateinit var MEM_SNS_ID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, "c4ab14465de77a0d0621a4f8f587bd5b")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mContext = this
        mKakaoApi = UserApiClient.instance
        binding.btnKakaoLogin.setOnClickListener {
            getAccessToken(mContext)
        }

    }

    private fun getAccessToken(mContext: Context) {
        mKakaoApi.loginWithKakaoAccount(mContext) { token, error ->
            if (error != null) {
                Log.e(tag, "getAccessToken 실패", error)
            } else if (token != null) {
                Log.e(tag, "getAccessToken 성공! ${token.accessToken}")

                setKakaoUserId()
            }
        }
    }

    private fun setKakaoUserId() {
        mKakaoApi.me { user, error ->
            if (error != null) {
                Log.e(tag, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(
                    tag, "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                )

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

        val call: Call<SocialLoginResponseModel> = userService.socialLogin(MEM_SNS_TYPE, MEM_SNS_ID)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<SocialLoginResponseModel> {
            override fun onResponse(
                call: Call<SocialLoginResponseModel>,
                response: Response<SocialLoginResponseModel>
            ) { // Response Success
                Log.d(tag, "성공 : ${response}")
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as SocialLoginResponseModel
                Log.d(tag, "성공 : ${resBody}")

                val MEM_ID = resBody.data?.get(0)?.MEM_ID
                Log.d(tag, "성공 : ${MEM_ID}")

                if (MEM_ID != null) {
                    resultProcess(MEM_ID, resBody.code)
                }
            }

            override fun onFailure(call: Call<SocialLoginResponseModel>, t: Throwable) {   // Response Fail
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
    private fun resultProcess(MEM_ID:Int, responseCode: Int) {
        when (responseCode) {
            423 -> {
                moveToUserJoin()
            }
            200 -> {
                removeAccessToken()
                moveToQnaList(MEM_ID)
            }
            else -> {
                Toast.makeText(this, "적절하지 못한 접근입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeAccessToken() {
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(tag, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(tag, "로그아웃 성공. SDK에서 토큰 삭제됨")
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

    private fun moveToQnaList(MEM_ID: Int) {
        saveMemId(MEM_ID)
        val sharedPref = this.getSharedPreferences("App", Context.MODE_PRIVATE)
        Log.e(tag, "MEM_ID: ${sharedPref.getInt("MEM_ID", -1)}")

        val intent = Intent(this, QnaActivity::class.java)
        this.startActivity(intent)
        this.finish() // 재로그인은 실시할 필요가 없으므로
    }

    private fun saveMemId(MEM_ID: Int) {
        val sharedPref = this.getSharedPreferences("App", Context.MODE_PRIVATE)
        sharedPref.edit {
            this.putInt("MEM_ID", MEM_ID)
//            this.apply()
            this.commit()

            // apply()는 메모리 내 SharedPreferences 객체를 즉시 변경하지만
            // 업데이트를 디스크에 비동기적으로 씁니다.
            // 또는 commit()을 사용하여 데이터를 디스크에 동기적으로 쓸 수 있습니다.
            // 그러나 commit()은 동기적이므로 기본 스레드에서 호출하는 것을 피해야 합니다. UI 렌더링이 일시중지될 수 있기 때문입니다.
        }
    }
}