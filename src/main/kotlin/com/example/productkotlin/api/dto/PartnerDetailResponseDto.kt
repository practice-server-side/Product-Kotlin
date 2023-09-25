package com.example.productkotlin.api.dto


data class PartnerDetailResponseDto(
    val partners: List<Partner>
) {
    data class Partner (
        val partnerId: Long,
        val partnerName: String,
    )
}

