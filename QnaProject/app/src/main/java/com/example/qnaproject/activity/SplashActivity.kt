package com.example.qnaproject.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.R
import com.example.qnaproject.databinding.ActivitySplashBinding

/**
 * Splash화면을 보여주며
 * SharedPreference의 `MEM_ID` 존재여부 확인
 */
class SplashActivity: AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding

    private val tag = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        val sharedPref = this.getSharedPreferences("App", Context.MODE_PRIVATE)
        val MEM_ID = sharedPref.getInt("MEM_ID", -1)
        Log.e(tag, "MEM_ID: ${MEM_ID}")

        if(MEM_ID != -1) {  // 기존의 MEM_ID가 저장되어있다면..
            // 문의리스트 화면 이동
//            val intent = Intent(this, QnaActivity::class.java)
//            this.startActivity(intent)
//            this.finish()
            // 상품관련 화면 이동
            val intent = Intent(this, ProductActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
        else {    // 기존의 MEM_ID가 없다면..
            // MainActivity로 이동(로그인 및 회원가입)
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
    }
}

