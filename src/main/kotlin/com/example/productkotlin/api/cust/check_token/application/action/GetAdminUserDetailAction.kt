package com.example.productkotlin.api.cust.check_token.application.action

import com.example.productkotlin.api.cust.check_token.application.port.`in`.GetAdminUserDetailUseCase
import com.example.productkotlin.api.service.NoSuchExceptionService
import org.springframework.stereotype.Component

@Component
class GetAdminUserDetailAction(
    private val noSuchExceptionService: NoSuchExceptionService,
) : GetAdminUserDetailUseCase {
    override fun getAdminUserDetail(currentCustId: Long): GetAdminUserDetailUseCase.GetAdminUserDetailResponseDto {
        val requestCust = noSuchExceptionService.validateCust(currentCustId)

        return GetAdminUserDetailUseCase.GetAdminUserDetailResponseDto(
            custName = requestCust.custName,
            custPhone = requestCust.custPhone,
            custKey = requestCust.custKey,
        )
    }
}
