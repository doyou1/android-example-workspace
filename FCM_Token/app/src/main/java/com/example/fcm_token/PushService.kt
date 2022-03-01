package com.example.fcm_token

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val serverKey = "AAAAcZdD_kc:APA91bHLAm6WbAKYlk93DNRLYSZGRRj72xo7lEXZN_BeZQFXf-WYYML56CmUWOg3xPgHLIzQ-eI36hmBkYd7lFLZeOaB8PjneqU3skB6opyyx_PDqcCJ4ZHMa0xMf-2wypKeQhdQ3IhD"
interface PushService {


    @Headers(
        "content-type: application/json",
        "Authorization: key=$serverKey"
    )
    @POST("fcm/send")
    fun sendFcm(@Body body: RequestBody): Call<PushResponseModel>

}