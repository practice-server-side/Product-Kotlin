package com.example.productkotlin.api.cust.no_check_token.application.action

import com.example.productkotlin.api.cust.no_check_token.application.port.`in`.PostAdminUserJoinCommand
import com.example.productkotlin.api.cust.no_check_token.application.port.`in`.PostAdminUserJoinUseCase
import com.example.productkotlin.api.model.Cust
import com.example.productkotlin.api.repository.CustRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class PostAdminUserJoinAction(
    private val custRepository: CustRepository,
    private val passwordEncoder: PasswordEncoder,
) : PostAdminUserJoinUseCase {
    override fun adminUserJoin(command: PostAdminUserJoinCommand) {
        if (custRepository.existsByLoginId(command.loginId!!)) {
            throw DuplicateKeyException("이미 사용중인 아이디 입니다.")
        }

        val newData = Cust(
            loginId = command.loginId!!,
            loginPassword = passwordEncoder.encode(command.loginPassword!!),
            custName = command.userName!!,
            custPhone = command.userPhone!!,
            custKey = UUID.randomUUID().toString(),
        )

        custRepository.save(newData)
    }
}
