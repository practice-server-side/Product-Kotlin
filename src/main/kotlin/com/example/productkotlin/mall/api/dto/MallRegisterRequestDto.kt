package com.example.productkotlin.mall.api.dto

import jakarta.validation.constraints.NotBlank

data class MallRegisterRequestDto (
    @field:NotBlank(message = "몰 이름은 비어있을 수 없습니다.")
    val mallName: String?,
)