package com.example.productkotlin.api.dto

data class CustJoinRequestDto (
    val loginId: String,
    val loginPassword: String,
    val userName: String,
    val userPhone: String,
)