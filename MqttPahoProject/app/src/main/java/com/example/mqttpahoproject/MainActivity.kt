package com.example.mqttpahoproject

import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mqttpahoproject.Const.DEV_ID
import com.example.mqttpahoproject.Const.baseUrl
import com.example.mqttpahoproject.Const.requestNetworkQoS
import com.example.mqttpahoproject.Const.requestNetworkTopic
import com.example.mqttpahoproject.Const.sendNetworkQoS
import com.example.mqttpahoproject.Const.sendNetworkTopic
import com.example.mqttpahoproject.databinding.ActivityMainBinding
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName.toString()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mqttAndroidClient: MqttAndroidClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // connectMqtt -> subscribeMqtt -> setReceiveCallback
        connectMqtt()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnRequest.setOnClickListener {
            requestNetworkStatus()
        }

        binding.btnSend.setOnClickListener {
            subscribeSendNetworkStatus()
        }
    }

    private fun connectMqtt() {
        // param: context, 브로커 ip 주소, client id(paho가 자동으로 생성)
        mqttAndroidClient = MqttAndroidClient(this, baseUrl, MqttClient.generateClientId())

        try {
            val token:IMqttToken = mqttAndroidClient.connect(getMqttConnectOptions())
            token.actionCallback = object: IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) { // connect success
                    Log.e(TAG, "Connect Success")
                    mqttAndroidClient.setBufferOpts(getDisconnectedBufferOptions())
//                    subscribeSendNetworkStatus()
//                    requestNetworkStatus()
                }
                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "Connect Fail ${exception.toString()}")
                }
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    // 네트워크 상태정보 요청
    private fun requestNetworkStatus() {
        try {
            val message = MqttMessage()
            val map = JSONObject()
            map.put("DEV_ID", DEV_ID)

            message.payload = map.toString().toByteArray()
            mqttAndroidClient.publish(requestNetworkTopic, message.payload, requestNetworkQoS, false)
            binding.tvRequestResult.text = "요청 성공!"
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    private fun subscribeSendNetworkStatus() {
        try {
            mqttAndroidClient.subscribe(sendNetworkTopic, sendNetworkQoS, null, object: IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    binding.tvSubscribeResult.text = "구독 성공!"
                    setSendCallback()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "subscribe onFailure $asyncActionToken, $exception")
                }

            })

        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    private fun setSendCallback() {
        // 발행자로부터의 데이터 송신 callback
        mqttAndroidClient.setCallback(object: MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                Log.e(TAG, "connectionLost ${cause?.printStackTrace()}")
            }
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.e(TAG, "topic $topic")
                Log.e(TAG, "payload ${message?.payload?.toString()}")

                val dataJson  = JSONObject(String(message?.payload!!))

                binding.tvSubscribeResult.text = "topic $topic, payload $dataJson"
            }
            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.e(TAG, "deliveryComplete ${token?.message}")
                binding.tvSubscribeResult.text = "token.topics ${token?.topics} ${token?.message}"
            }
        })
    }

    private fun getMqttConnectOptions() : MqttConnectOptions {
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isCleanSession = false
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.connectionTimeout = MqttConnectOptions.CONNECTION_TIMEOUT_DEFAULT
        mqttConnectOptions.keepAliveInterval = MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT

        return mqttConnectOptions
    }

    private fun getDisconnectedBufferOptions() : DisconnectedBufferOptions{
        val disconnectedBufferOptions = DisconnectedBufferOptions()
        disconnectedBufferOptions.isBufferEnabled = true
        disconnectedBufferOptions.bufferSize = 100
        disconnectedBufferOptions.isPersistBuffer = false
        disconnectedBufferOptions.isDeleteOldestMessages = false

        return disconnectedBufferOptions
    }
}

/**
 * subscribe 할때 3번째 파라메터에 익명함수 리스너를 달아줄수도있음
 */
/*try {
    mqttAndroidClient.subscribe("jmlee!!", 0, new IMqttMessageListener() {
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
        }
    });
} catch (MqttException e) {
    e.printStackTrace();
}*/