package com.example.fcm_token

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fcm_token.databinding.ActivityPushBinding
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PushActivity: AppCompatActivity() {

    private val baseUrl = "https://fcm.googleapis.com/"
    private lateinit var binding: ActivityPushBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnPush.setOnClickListener {

            pushFcm()
            Toast.makeText(applicationContext,
                "text: ${binding.etText.text.toString()}, image: ${binding.etImage.text.toString()}",
                Toast.LENGTH_SHORT).show()
        }
        binding.btnMoveToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(applicationContext,
                "Move to Main",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun pushFcm() {
        val text = binding.etText.text.toString()
        val url = binding.etImage.text.toString()

        val jsonParams = mutableMapOf<String, Object>()
        jsonParams["notification"] = mutableMapOf("title" to "title", "body" to "text") as Object
        jsonParams["data"] = mutableMapOf("text" to text, "image" to url) as Object
        // 갤탭
        jsonParams["to"] = "c00x1QgVTOimKWwBrtsjXG:APA91bG5NRxq8fnL4EbjPNspx7ICxvie3c8zlTfW8ZLpa_b24T2LrgQ1ZsXFHUpqZh4tqARpwjIvcWWAfGj4thUvA98XSduFNyQhxw456xwcmT_Ruc3ECexxdVDcxaG6HCADE_-pWwDR" as Object

        // 테스트폰
//        jsonParams["to"] = "fl7c8q8cSPORQ3ek_qpuyu:APA91bGfdL9qHJ0C156kqa4QStMOGfovonfv5VIYdTvV3ftqbjzYAhhXqtXnxo7lYDW-hBNvcAneSu6ThbzfXRjKiKyjtE25WQ4D9R7qQaDP94UtoJu5-xdes6kJ6Nnd_i30ibrm8g4K" as Object


        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),(JSONObject(
            jsonParams as Map<String, Object>?
        )).toString());

        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val productService = retrofit.create(PushService::class.java)

        // List 데이터에 접근하는 call 객체 생성
        val call: Call<PushResponseModel> = productService.sendFcm(body)

        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<PushResponseModel> {
            override fun onResponse(
                call: Call<PushResponseModel>,
                response: Response<PushResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                Log.e(TAG, "response: $response")
                Log.e(TAG, "response: ${response.body()}")
                finish()
            }

            override fun onFailure(
                call: Call<PushResponseModel>,
                t: Throwable
            ) {   // Response Fail
                Log.d(TAG, "실패 : $t")
            }
        })

    }

    companion object {
        private const val TAG = "PushActivity"

        @Volatile
        private var instance: PushActivity? = null

        @JvmStatic
        fun getInstance(): PushActivity =
            instance ?: synchronized(this) {
                instance ?: PushActivity().also {
                    instance = it
                }
            }
    }


}