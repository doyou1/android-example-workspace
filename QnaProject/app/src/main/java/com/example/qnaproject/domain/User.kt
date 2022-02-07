package com.example.qnaproject.domain

/**
 * 회원가입(UserJoin) 인터페이스 URL 전송 데이터 모델
 *
 * MEM_ID 변수는 회원가입 URL 호출 -> 응답시 반환받는 멤버 아이디 (MEM_ID)를 담는다.
 * (UserResponseModel에서 활용)
 */
data class User (val MEM_SNS_TYPE: String, val MEM_SNS_ID: String, val MEM_NICK: String, val MEM_TEL: String?, val MEM_ID: Int?){
}