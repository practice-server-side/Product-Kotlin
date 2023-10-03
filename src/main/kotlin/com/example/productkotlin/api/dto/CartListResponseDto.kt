package com.example.productkotlin.api.dto

import com.example.productkotlin.api.dto.comoon.ResponseDto

class CartListResponseDto : ResponseDto {
    val carts: List<Cart>

    data class Cart (
        val productId: Long,
    )

    constructor(pageSize: Int, pageNumber: Int, totalCount: Long, carts: List<Cart>) : super(pageSize, pageNumber, totalCount) {
        this.carts = carts
    }

}