package com.example.ryougaesampling

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.btn_start).setOnClickListener {
            var currentPrice: Double? = null
            runBlocking {
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    currentPrice = RougaeUtil().getRougae()
                }
                currentPrice?.let {
                    Log.d(TAG, "currentPrice1: $currentPrice")
                }
            }
        }
    }
}

