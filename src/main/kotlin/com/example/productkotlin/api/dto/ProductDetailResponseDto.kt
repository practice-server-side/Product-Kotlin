package com.example.productkotlin.api.dto

data class ProductDetailResponseDto (
    val productId: Long,
    val productName: String,
    val productPrice: Int,
    val partnerId: Long,
    val partnerName: String,
)