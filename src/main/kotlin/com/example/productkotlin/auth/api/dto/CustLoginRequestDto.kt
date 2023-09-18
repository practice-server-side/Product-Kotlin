package com.example.productkotlin.auth.api.dto

data class CustLoginRequestDto (
    val loginId: String,
    val loginPassword: String,
)