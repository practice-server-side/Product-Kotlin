package com.example.productkotlin.api.cust.no_check_token.application.port.`in`

interface GetCheckAdminUserLoginIdUseCase {
    fun checkAdminUserLoginId(requestLoginId: String): CheckLoginIdResponseDto

    class CheckLoginIdResponseDto(
        val exists: Boolean,
    )
}
