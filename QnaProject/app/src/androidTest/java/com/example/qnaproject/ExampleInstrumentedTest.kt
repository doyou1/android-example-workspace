package com.example.qnaproject

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.qnaproject.responseModel.QnaResponseModel
import com.example.qnaproject.service.QnaService

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val baseUrl = "https://api.jamjami.co.kr/"
    //    private val url = "https://api.jamjami.co.kr/api/faq/FaqList?MEM_ID=73&PAGE=1"
    private val memId = 73
    private val page = 1

    private val tag = "QnaActivity"

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.qnaproject", appContext.packageName)
    }

    fun retrofitTest() {
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val qnaService = retrofit.create(QnaService::class.java)

        val call: Call<QnaResponseModel> = qnaService.getQnaList(memId, page)

        call.enqueue(object : Callback<QnaResponseModel> {
            override fun onResponse(
                call: Call<QnaResponseModel>,
                response: Response<QnaResponseModel>
            ) {
                Log.d(tag, "성공 : ${response.raw()}")
                Log.d(tag, "성공 : ${response.body()}")
                Log.d(tag, "성공 : ${response}")
            }

            override fun onFailure(call: Call<QnaResponseModel>, t: Throwable) {
                Log.d(tag, "실패 : $t")
            }
        })

    }
}