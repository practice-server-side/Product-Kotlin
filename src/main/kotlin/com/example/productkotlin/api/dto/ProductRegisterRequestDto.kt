package com.example.productkotlin.api.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class ProductRegisterRequestDto (
    @field:NotBlank(message = "상품 이름은 필수 입력 값입니다.")
    val productName: String,
    @field:Min(1, message = "0 또는 공백일 수 없습니다.")
    val productPrice: Int,
    @field:Min(1, message = "0 또는 공백일 수 없습니다.")
    val partnerId: Long,
    @field:Min(1, message = "0 또는 공백일 수 없습니다.")
    val mallId: Long,
)