package com.example.productkotlin.api.cust.check_token.adapter.`in`

import com.example.productkotlin.api.cust.check_token.application.port.`in`.GetAdminUserDetailUseCase

class GetAdminUserDetailResponse(
    val custName: String,
    val custPhone: String,
    val custKey: String,
)

fun GetAdminUserDetailUseCase.GetAdminUserDetailResponseDto.toResponse(): GetAdminUserDetailResponse {
    return GetAdminUserDetailResponse(
        custName = this.custName,
        custPhone = this.custPhone,
        custKey = this.custKey,
    )
}
