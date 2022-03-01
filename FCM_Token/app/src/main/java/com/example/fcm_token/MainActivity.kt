package com.example.fcm_token

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fcm_token.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseToken: String
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getFirebaseToken()
        getIntentData()
        Log.e(TAG, "onCreate")
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnMoveToPush.setOnClickListener {
            val intent = Intent(this, PushActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext, "Move To Push", Toast.LENGTH_SHORT).show()
        }
    }

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

    private fun getIntentData() {
        val url = intent.getStringExtra("image")
        val text = intent.getStringExtra("text")

        url?.let {
            text?.let {
                setDataInLayout(url, text)
            }
        }
    }

    private fun setDataInLayout(url: String, text: String) {

        Glide
            .with(applicationContext)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .error(R.drawable.firebase_lockup_400)
            .placeholder(R.drawable.firebase_lockup_400)
            .into(binding.ivImage)

        binding.tvText.text = text

    }

    companion object {
        private const val TAG = "MainActivity"

        @Volatile
        private var instance: MainActivity? = null

        @JvmStatic
        fun getInstance(): MainActivity =
            instance ?: synchronized(this) {
                instance ?: MainActivity().also {
                    instance = it
                }
            }
    }
}