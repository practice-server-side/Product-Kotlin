package com.example.productkotlin.api.dto

import com.example.productkotlin.api.dto.comoon.ResponseDto

class ProductListResponseDto : ResponseDto {
    val products: List<Product>

    data class Product(
        val productId: Long,
        val productName: String,
        val productPrice: Int,
        val stock: Int,
    )

    constructor(pageSize: Int, pageNumber: Int, totalCount: Long, products: List<Product>) :
        super(pageSize, pageNumber, totalCount) {
        this.products = products
    }
}
