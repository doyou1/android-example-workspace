package com.example.qnaproject

import com.google.gson.JsonArray

/**
 * 인터페이스의 Response Body의 모양에 따른 Custom ResponseModel
 *
 * {
code: 200,
message: "",
data: [
{
...
},
,
...
],
}
 */
data class ResponseModel(val code:Int, val message:String, val data: JsonArray?) {

    override fun toString(): String {
        return "code: ${code}, message: ${message}, data: ${data}"
    }
}