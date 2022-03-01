package com.example.fcmproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.fcmproject.databinding.ActivityMainBinding
import com.example.fcmproject.databinding.ActivityReceiveBinding

class ReceiveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceiveBinding


    /**
     * 현재 ReceiveActivity는 `Foreground`상태에서 FCM을 수신할 경우 생성된다.
     *
     * Foreground인 상태에서
     * FCM의
     * `notification`, `data` 모두 FirebaseMessagingService의 `onMessageReceived`로 전송된다
     * FirebaseMessagingService을 implement한 FCMService의 `onMessageReceived`를 보면
     *
     * ReceiveActivity로 이동하기 위한 Intent 생성 ->
     * 알림 탭 이후 액티비티 이동(지연 수행)을 하기 위해 PendingIntent 선언(+ add intent) ->
     * Notification (푸시알림) 선언 (진동, 사운드, 알림 레이아웃 등) 및 notify ->
     * 사용자가 알림 확인 및 클릭 ->
     * Intent 실행 ->
     * ReceiveActivity 생성
     *
     * 위와 같은 과정으로 처리가 진행된다.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_receive)
        getIntentData()
    }


    /**
     * onMessageReceived의 intent에 담겨있는 extras를 확인해 Toast 메세지, 레이아웃 설정 등의 처리를 수행한다.
     */
    private fun getIntentData() {
        val isForeground = intent.extras?.get("isForeground") as Boolean?
        if (isForeground == true) {
            setDataFromForeground()
        } else {
            setDataFromBackground()

        }
    }

    private fun setDataFromForeground() {
        val text = intent.extras?.get("text").toString()
        val notification_title = intent.extras?.get("notification_title").toString()
        val notification_body = intent.extras?.get("notification_body").toString()

        binding.tvText.text = text

        Toast.makeText(
            this,
            "notification_title: $notification_title, notification_body: $notification_body",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setDataFromBackground() {
        val text = intent.extras?.get("text").toString()

        binding.tvText.text = text

        Toast.makeText(
            this,
            "Launch ReceiveActivity from Background",
            Toast.LENGTH_SHORT
        ).show()
    }
}