package com.example.productkotlin.api.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class ProductRegisterRequestDto (
    @field:NotBlank(message = "상품 이름은 필수 입력 값입니다.")
    val productName: String,
    @field:Min(1)
    val productPrice: Int,
    @field:Min(1)
    val partnerId: Long,
    @field:Min(1)
    val mallId: Long,
)