package com.example.productkotlin.api.dto

import jakarta.validation.constraints.Min

data class CartRegisterRequestDto (
    @field:Min(1, message = "0 또는 공백일 수 없습니다.")
    val productId: Long,
)