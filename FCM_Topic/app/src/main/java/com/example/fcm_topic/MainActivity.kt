package com.example.fcm_topic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.fcm_topic.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class MainActivity : AppCompatActivity() {

    private val topic = "hellobiz"
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        getIntentData()
        setClickEvent()
    }

    private fun getIntentData() {
        intent.extras?.let {
            binding.tvText.text = it.get("text").toString()
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                Log.e(TAG, "Key: $key Value: $value")
            }
        }
    }

    private fun setClickEvent() {
        binding.btnSubscribe.setOnClickListener {
            Log.e(TAG, "btnSubscribe")
            Firebase.messaging.subscribeToTopic(topic)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.e(TAG, "Subscribe Fail")
                        Toast.makeText(this, "Subscribe Fail!", Toast.LENGTH_SHORT).show()
                    }
                    Log.e(TAG, "Subscribe Success")
                    Toast.makeText(this, "Subscribe Success!", Toast.LENGTH_SHORT).show()
                }
        }

        binding.btnLogToken.setOnClickListener {
            // Get token
            Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                Log.e(TAG, "token: $token")

                Toast.makeText(this, "token: $token", Toast.LENGTH_SHORT).show()
            })
        }
        binding.btnUnsubscribe.setOnClickListener {
            // Get token
            Firebase.messaging.unsubscribeFromTopic(topic)
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.e(TAG, "Unsubscribe Fail")
                        Toast.makeText(this, "Unsubscribe Fail!", Toast.LENGTH_SHORT).show()
                    }
                    Log.e(TAG, "Unsubscribe Success")
                    Toast.makeText(this, "Unsubscribe Success!", Toast.LENGTH_SHORT).show()
            })
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}