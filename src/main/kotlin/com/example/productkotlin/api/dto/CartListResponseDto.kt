package com.example.productkotlin.api.dto

data class CartListResponseDto (
    val carts: List<Cart>
) {
    data class Cart (
        val productId: Long,
    )
}