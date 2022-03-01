package com.example.qnaproject.domain

/**
 * Product (상품리스트 아이템 모델)
 */
<<<<<<< HEAD
data class Product(
    val ITM_ID: Int?,
    val ITM_IMG1: String?,  // 상품 등록, 수정 중 이미지데이터가 없는 경우
    val ITM_NM: String,
    val ITM_PRICE: String,
    val ITM_DIS: String,
    val ITM_INTRO: String,
    val ITM_DIS_PRICE: String?,
    val ITM_VIEW_YN: String
) {
=======
data class Product (val ITM_ID: Int, val ITM_IMG1: String, val ITM_NM: String, val ITM_PRICE: String, val ITM_DIS: Int, val ITM_INTRO: String, val ITM_DIS_PRICE: Int){
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
}