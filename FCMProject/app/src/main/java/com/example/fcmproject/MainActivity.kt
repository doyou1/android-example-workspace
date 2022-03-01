package com.example.fcmproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.fcmproject.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseToken:String
    private lateinit var binding: ActivityMainBinding

    /**
     * 현재 MainActivity는 `Background`상태에서 FCM을 수신할 경우 열면.
     * `notification`의 `click_action`에서 설정할 수도 있지만,
     * 사용자가 알림을 클릭하면 기본적으로 앱 런처가 열림(MainActivity)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        getFirebaseToken()
        getIntentData()
    }

    /**
     * FCM 송수신을 위한 token을 구하는 메서드
     *
     * 토근의 만료는 정해지지 않았다.
     * 아래의 이벤트 이외에는 만료X
     * - 앱이 인스턴스 ID를 삭제한 경우
     * - 앱이 새 기기에서 복원되었을 경우
     * - 사용자가 앱을 제거/재설치한 경우
     * - 사용자가 앱 데이터를 지운 경우
     *
     * 앱 설치 후 최초 실행 시
     * FirebaseMessaging.getInstance().token이 null이 나온경우
     * FirebaseMessagingService.onNewToken()에서 토큰이 발행됨
     */
    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            firebaseToken = task.result.toString()
            Log.e(TAG, "firebaseToken: $firebaseToken")
        })
    }

    /**
    * Background인 상태에서
    * FCM의
    * `notification` = `작업표시줄`,
    * `data = `인텐트 부가 정보(intent extra)`
     * 으로 제공된다.
    *
    * 작업표시줄의 알림을 클릭할 경우, 열린 액티비티에 FCM의 `data`는 intent extra로 전송된다.
    */
    private fun getIntentData() {

        /**
         * ex)
         * "data" : {
         *      "text": "데이터 텍스트입니다123",
         * }
         */
        val text = intent.getStringExtra("text")    // value : "데이터 텍스트입니다123"
        text?.let {
            binding.tvText.text = it
            Log.e(TAG, "text: $it")
        }
    }

    companion object {
        private const val TAG = "MainActivity"

    }
}