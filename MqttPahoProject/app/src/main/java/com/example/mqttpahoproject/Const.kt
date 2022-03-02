package com.example.mqttpahoproject

object Const {
    const val DEV_ID = "70"
    const val requestNetworkTopic = "flower/network/req/$DEV_ID"
    const val requestNetworkQoS = 2

    const val sendNetworkTopic = "flower/network/send/$DEV_ID"
    const val sendNetworkQoS = 2

    const val baseUrl = "tcp://13.125.21.79:1883"
}