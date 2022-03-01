package com.example.fcm_token

import com.google.gson.JsonArray

data class PushResponseModel(val multicast_id: Long, val success: Int, val failure: Int, val canonical_ids: Int, val result: JsonArray) {

}