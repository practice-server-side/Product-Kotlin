package com.example.productkotlin.api.cust.no_check_token.application.action

import com.example.productkotlin.api.cust.no_check_token.application.port.`in`.GetCheckAdminUserLoginIdUseCase
import com.example.productkotlin.api.cust.no_check_token.application.port.`in`.GetCheckAdminUserLoginIdUseCase.CheckLoginIdResponseDto
import com.example.productkotlin.api.repository.CustRepository
import org.springframework.stereotype.Component

@Component
class GetCheckAdminUserLoginIdAction(
    private val custRepository: CustRepository,
) : GetCheckAdminUserLoginIdUseCase {
    override fun checkAdminUserLoginId(
        requestLoginId: String,
    ): CheckLoginIdResponseDto {
        return CheckLoginIdResponseDto(exists = custRepository.existsByLoginId(requestLoginId))
    }
}
