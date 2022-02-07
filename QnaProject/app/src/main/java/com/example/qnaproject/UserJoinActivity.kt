package com.example.qnaproject

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.ActivityUserJoinBinding

class UserJoinActivity: AppCompatActivity() {

    private lateinit var binding:ActivityUserJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState,)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_join)

        
        binding.toolbarUserJoin.ibBack.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnQnaRegister.setOnClickListener{

        }
    }
}