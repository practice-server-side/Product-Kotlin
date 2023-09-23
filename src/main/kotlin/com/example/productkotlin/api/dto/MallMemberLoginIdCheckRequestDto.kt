package com.example.productkotlin.api.dto

import jakarta.validation.constraints.NotBlank

data class MallMemberLoginIdCheckRequestDto (
    @field:NotBlank(message = "몰 키는 필수 입력값입니다.")
    val mallKey: String?,
)