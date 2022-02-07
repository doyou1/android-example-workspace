package com.example.qnaproject

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.util.Utility

class StartActivity: AppCompatActivity() {


    private val tag = "StartActivity"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        var keyHash = Utility.getKeyHash(this)

        Log.e(tag, "Keyhash: ${keyHash}")
    }
}