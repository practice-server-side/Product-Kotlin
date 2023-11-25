package com.example.productkotlin.api.cust.application.port.`in`

interface GetAdminUserDetailUseCase {
    fun getAdminUserDetail(currentCustId: Long): GetAdminUserDetailResponseDto

    class GetAdminUserDetailResponseDto(
        val custName: String,
        val custPhone: String,
        val custKey: String,
    )
}
