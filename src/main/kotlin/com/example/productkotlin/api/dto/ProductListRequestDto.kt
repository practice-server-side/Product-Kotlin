package com.example.productkotlin.api.dto

import jakarta.validation.constraints.Min

data class ProductListRequestDto (
    var productName: String?,
    @field:Min(0, message = "0보다 작거나 공백일 수 없습니다.")
    val pageNumber: Int,
    @field:Min(0, message = "0보다 작거나 공백일 수 없습니다.")
    val pageSize: Int,
)