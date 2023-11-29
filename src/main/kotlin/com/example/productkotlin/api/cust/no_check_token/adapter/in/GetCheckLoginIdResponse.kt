package com.example.productkotlin.api.cust.no_check_token.adapter.`in`

import com.example.productkotlin.api.cust.no_check_token.application.port.`in`.GetCheckAdminUserLoginIdUseCase

class GetCheckLoginIdResponse(
    val exists: Boolean,
)

fun GetCheckAdminUserLoginIdUseCase.CheckLoginIdResponseDto.toResponse(): GetCheckLoginIdResponse {
    return GetCheckLoginIdResponse(
        exists = this.exists,
    )
}
