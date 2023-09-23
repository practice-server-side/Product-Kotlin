package com.example.productkotlin.api.dto

data class MallMemberCreateRequestDto (
    val loginId: String,
    val loginPassword: String,
    val memberName: String,
    val mallKey: String,
)