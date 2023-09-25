package com.example.productkotlin.api.dto

data class PartnerDetailResponseDto (
    val partnerId: Long,
    val partnerName: String,
    val partnerCompanyNo: String,
    val mallId: Long,
    val mallName: String,
)