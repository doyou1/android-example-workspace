package com.example.fcmproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val REQEUST_CODE = 0
class FCMService: FirebaseMessagingService()  {

    private val channelId = "notification_channel"
    private val channelName = "com.example.fcm_topic"

    /**
     * `Foreground`인 상태에서 FCM을 수신(receive)한 경우
     * onMessageReceived이 호출된다. 보내진 FCM 메시지 정보들은 파라미터인 remoteMessage에 담겨있다.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.e(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.e(TAG, "Message data payload: ${remoteMessage.data}")
        }
        Log.e(TAG, "remoteMessage.notification.title: ${remoteMessage.notification?.title}")
        Log.e(TAG, "remoteMessage.notification.body: ${remoteMessage.notification?.body}")


        // Foreground에서도 Background와 같이 알림을 발생시킴
        sendNotification(remoteMessage.notification, remoteMessage.data)
//        moveToReceiveActivity(remoteMessage.notification, remoteMessage.data)

    }

    /**
     * 알림 생성 및 알림 클릭 이후 처리를 설정하는 메서드
     */
    private fun sendNotification(notification: RemoteMessage.Notification?, data: Map<String, String>?) {

        // 현재 액티비티에서 Receive 액티비티로의 이동 및 데이터 전송을 처리하기 위한 Intent 객체
        val intent = Intent(this, ReceiveActivity::class.java)
        /**
         * Intent의 Flag가 필요한 이유
         * - Activity를 호출하다보면 Activity에 대해 중복 문제가 발생하곤 한다.
         *  이때 중복을 방지하거나 Activity에 대한 흐름을 제어하고 싶을 때 사용한다.
         * - Manifest에선 android:launchMode 속성으로 제어할 수 있다.
         *
         * addFlags() : 새로운 flag를 기존 flag에 추가
         * setFlags() : 이전 flag를 전체 대체
         *
         *- FLAG_ACTIVITY_CLEAR_TOP
         * : 호출하는 Activity가 스택에 있을 경우(setContentIntent(pendingIntent(intent))),
         * 해당 Activity를 최상위로 올리면서 그 위에 있던 Activity들을 모두 삭제하는 Flag
         * : stack에 쌓여있는 나머지 Activity를 모두 onDestroy() 시키는 Flag
         */
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("isForeground", true)
        intent.putExtra("text", data?.get("text").toString())
        intent.putExtra("notification_title", notification?.title)
        intent.putExtra("notification_body", notification?.body)

        /**
         * PendingIntent가 필요한 이유
         * - Intent를 당장 수행하지 않고 특정 시점(특히, 앱이 구동되고 있지 않을 때)에 수행하도록 함
         *
         * 여러 컴포넌트(Activity, Service, BoardcastReceiver)의 수행을 특정 시점에 수행하도록 할 수 있다.
         *
         * - FLAG_ONE_SHOT
         * : 해당 PendingIntent를 일회성으로 사용
         * - FLAG_UPDATE_CURRENT
         * : 이미 생성된 PendingIntent가 있따면, Extra Data만 갈아끼움
         */
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification?.title)
            .setContentText(notification?.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo, notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    /**
     * FCM을 전송받은 경우, 곧바로 ReceiveActivity로 이동하는 메서드
     */
    private fun moveToReceiveActivity(
        notification: RemoteMessage.Notification?,
        data: Map<String, String>
    ) {
        val intent = Intent(applicationContext, ReceiveActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("text", data["text"].toString())
        intent.putExtra("notification_title", notification?.title)
        intent.putExtra("notification_body", notification?.body)

        startActivity(intent)
    }

    /**
     * 새로운 토큰이 발행된 경우 호출되는 함수
     */
    override fun onNewToken(newToken: String) {
        Log.e(TAG, "newToken: $newToken")
    }

    companion object {
        private const val TAG = "FCMService"
    }


}