package com.example.qnaproject.domain

/**
 * Kakao user.id으로
 * 로그인(회원조회) 인터페이스 URL을 접근한 결과 모델
 */
data class SocialUser (val MEM_ID: Int, val MEM_NICK: String, val MEM_STATE: String, val MEM_TOKEN: String?){
}