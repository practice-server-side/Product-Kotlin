package com.example.productkotlin.api.dto

data class CustJoinRequestDto (
    var loginId: String,
    var loginPassword: String,
    var userName: String,
    var userPhone: String,
)