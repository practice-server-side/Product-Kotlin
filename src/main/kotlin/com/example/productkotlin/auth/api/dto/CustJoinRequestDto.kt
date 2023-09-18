package com.example.productkotlin.auth.api.dto

data class CustJoinRequestDto (
    val loginId: String,
    val loginPassword: String,
    val userName: String,
    val userPhone: String,
)