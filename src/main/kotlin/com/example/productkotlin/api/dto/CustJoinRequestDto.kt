package com.example.productkotlin.api.dto

import jakarta.validation.constraints.NotBlank

data class CustJoinRequestDto (
    @field:NotBlank(message = "아이디는 필수 입력 값입니다.")
    val loginId: String?,
    @field:NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    val loginPassword: String?,
    @field:NotBlank(message = "이름은 필수 입력 값입니다.")
    val userName: String?,
    @field:NotBlank(message = "휴대폰 번호는 필수 입력 값입니다")
    val userPhone: String?,
)