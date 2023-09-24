package com.example.productkotlin.api.dto

import jakarta.validation.constraints.NotBlank

data class MallMemberLoginRequestDto (
    @field:NotBlank(message = "몰 키는 필수 입력 값입니다.")
    val mallKey: String,
    val loginRequestDto: LoginRequestDto,
)