package com.example.productkotlin.api.dto

import jakarta.validation.constraints.NotBlank

data class CustLoginRequestDto (
    @field:NotBlank(message = "아이디 비밀번호는 필수 입력 값입니다.")
    val loginId: String?,
    @field:NotBlank(message = "아이디 비밀번호는 필수 입력 값입니다.")
    val loginPassword: String?,
)