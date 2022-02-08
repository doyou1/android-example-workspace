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

/**
 * 카카오 로그인 및 회원가입을 처리하는 화면
 */
class MainActivity:AppCompatActivity() {
    private val baseUrl = "https://api.jamjami.co.kr/"

    private val tag = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var mContext: Context
    private lateinit var mKakaoApi: UserApiClient   // 카카오로그인 접근 객체

    private val MEM_SNS_TYPE = "K"  // 소셜 로그인 유형 변수
    private lateinit var MEM_SNS_ID: String // 카카오로그인 이후 카카오서버로부터의 유저 ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, "c4ab14465de77a0d0621a4f8f587bd5b")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mContext = this
        mKakaoApi = UserApiClient.instance  // 카카오로그인 접근 객체
        binding.btnKakaoLogin.setOnClickListener {  // 카카오로그인 버튼 클릭시
            getAccessToken(mContext)                // 카카오로그인 이후 AccessToken 리턴
        }

    }

    /**
     * 카카오서버로부터의 AccessToken을 확인하는 메소드
     * AccessToken이 발급돼야 유저정보 접근이 가능
     */
    private fun getAccessToken(mContext: Context) {
        mKakaoApi.loginWithKakaoAccount(mContext) { token, error ->
            if (error != null) {
                Log.e(tag, "getAccessToken 실패", error)
            } else if (token != null) {
                Log.e(tag, "getAccessToken 성공! ${token.accessToken}")

                setKakaoUserId()    // AccessToken 발급시 유저정보 접근
            }
        }
    }

    /**
     * AccessToken이 발급되면
     * 유저 정보에 접근하고, 회원가입 유무 판단을 위해
     * 유저 id에 접근 및 `MEM_SNS_ID`에 담기
     */
    private fun setKakaoUserId() {
        mKakaoApi.me { user, error ->
            if (error != null) {
                Log.e(tag, "사용자 정보 요청 실패", error)
            } else if (user != null) {  // 유저 정보 획득 성공
                Log.i(
                    tag, "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                )

                MEM_SNS_ID = user.id.toString()
                serverLogin()
            }
        }
    }

    /**
     * 카카오로부터 얻은 유저 ID로
     * 회원가입 여부 확인
     */
    private fun serverLogin() {
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

                if (MEM_ID != null) {   // 가입된 회원일 경우 - MEM_ID
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
            423 -> {    // 회원이 아닐시 회원가입화면으로 이동
                moveToUserJoin()
            }
            200 -> {    // 기존 회원시 정보화면으로 이동
                removeAccessToken() // 카카오 AccessToken 제거 (로그인 여부는 MEM_ID를 통해 실시)
                saveMemId(MEM_ID)   // Save in SharedPreference
                moveToProduct()     // 로그인 처리 이후 상품관련 화면으로 이동
            }
            else -> {
                Toast.makeText(this, "적절하지 못한 접근입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 서버와의 로그인이 완료되면,
     * 카카오의 AccessToken을 제거함.
     * (실제 로그아웃시, SharedPreference의 MEM_ID만 제거하면 로그아웃 완료)
     * 인증 절차는 모두 끝났으므로, SharedPreference의 MEM_ID만 로그인 여부 확인 가능
     */
    private fun removeAccessToken() {
        // 로그아웃
        mKakaoApi.logout { error ->
            if (error != null) {
                Log.e(tag, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(tag, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    /**
     * 회원가입 화면으로 이동
     * 회원가입시 필요한 소셜로그인 유형 정보(MEM_SNS_TYPE), 소셜아이디 정보(MEM_SNS_ID) 함께 전송
     */
    private fun moveToUserJoin() {
        val intent = Intent(this, UserJoinActivity::class.java)
        intent.putExtra("MEM_SNS_TYPE", MEM_SNS_TYPE)
        intent.putExtra("MEM_SNS_ID", MEM_SNS_ID)

        this.startActivity(intent)
//        this.finish() // onBackPressed 동작을 위한 이전 액티비티 not finish
    }

    /**
     * 상품 관련 화면으로 이동
     */
    private fun moveToProduct() {
        val intent = Intent(this, ProductActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
    
//    private fun moveToQnaList(MEM_ID: Int) {
//        saveMemId(MEM_ID)
//        val sharedPref = this.getSharedPreferences("App", Context.MODE_PRIVATE)
//        Log.e(tag, "MEM_ID: ${sharedPref.getInt("MEM_ID", -1)}")
//
//        val intent = Intent(this, QnaActivity::class.java)
//        this.startActivity(intent)
//        this.finish() // 재로그인은 실시할 필요가 없으므로
//    }

    /**
     * save MEM_ID in SharedPreference(name: "App")
     */
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