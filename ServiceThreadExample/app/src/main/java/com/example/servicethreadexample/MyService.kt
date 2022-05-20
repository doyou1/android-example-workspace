package com.example.servicethreadexample

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.widget.Toast

class MyService : Service() {

    private lateinit var nm: NotificationManager
    private var thread: ServiceThread? = null
    private lateinit var notification: Notification
    private lateinit var mContext: Context

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mContext = this
        nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val handler = MyServiceHandler()
        thread = ServiceThread(handler = handler, System.currentTimeMillis())
        thread?.start()

        return START_STICKY
    }

    override fun onDestroy() {
        thread?.stopForever()
        thread = null
    }

     inner class MyServiceHandler: Handler(Looper.getMainLooper()) {
         override fun handleMessage(msg: Message) {
             if (msg.what == -1) {
                 thread?.stopForever()
                 thread = null

                 Toast.makeText(this@MyService, "완료", Toast.LENGTH_SHORT).show()

                 stopSelf()
             } else {
                 val currentTime = msg.obj as Long
                 Toast.makeText(this@MyService, "토스트 메시지 $currentTime", Toast.LENGTH_SHORT).show()
             }
         }
     }
}