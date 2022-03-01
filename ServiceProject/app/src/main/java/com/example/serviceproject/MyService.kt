package com.example.serviceproject

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.lang.Exception

class MyService : Service() {

    private val tag = "MyService"
    var count = 0
    // 외부로 데이터를 전달하려면 바인더 사용

    // Binder 객체는 IBinder 인터페이스 상속구현 객체입니다
    //public class Binder extends Object implements IBinder
    val binder = MyServiceBinder()

    override fun onBind(intent: Intent): IBinder {
        Log.e(tag, "onBind")
        return binder
    }

    fun plusCount() {
        count++
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(tag, "onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e(tag, "onStartCommand")

        progressCommand(intent, flags, startId)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun progressCommand(intent: Intent, flags: Int, startId: Int) {
        val command = intent.getStringExtra("command")
        val text = intent.getStringExtra("text")

        Log.e(tag, "command: $command, text: $text, flags: $flags, startId: $startId")

        for (i: Int in 0..5) {
            try {
                Thread.sleep(1000)
            } catch (e: Exception) { e.printStackTrace() }

            Toast.makeText(applicationContext, "Wating: $i sec", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(tag, "onStartCommand")
    }

    inner class MyServiceBinder : Binder() {
        fun getService(): MyService {
            return this@MyService
        }
    }
}