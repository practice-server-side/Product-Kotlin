package com.example.productkotlin.api.dto

import com.example.productkotlin.api.dto.comoon.ResponseDto


class PartnerListResponseDto : ResponseDto {
    var partners: List<Partner>

    data class Partner (
        val partnerId: Long,
        val partnerName: String,
    )

    constructor(pageSize: Int, pageNumber: Int, totalCount: Long, partners: List<Partner>)
        : super(pageSize, pageNumber, totalCount) {
        this.partners = partners
    }
}