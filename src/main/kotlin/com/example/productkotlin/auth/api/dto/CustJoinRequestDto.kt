package com.example.productkotlin.auth.api.dto

import jakarta.validation.constraints.NotBlank

data class CustJoinRequestDto (
    @NotBlank
    val loginId: String,
    @NotBlank
    val loginPassword: String,
    @NotBlank
    val userName: String,
    @NotBlank
    val userPhone: String,
)