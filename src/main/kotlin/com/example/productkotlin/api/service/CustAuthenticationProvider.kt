package com.example.productkotlin.api.service

import com.example.productkotlin.api.model.CustCustomUserDetails
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Qualifier("custAuthenticationProvider")
@Primary
class CustAuthenticationProvider(
    private val custCustomUserDetailsService: CustCustomUserDetailsService,
    private val passwordEncoder: PasswordEncoder,
) : AuthenticationManager {

    override fun authenticate(authentication: Authentication): Authentication {
        val loginId = authentication.name
        val loginPassword = authentication.credentials as String

        val user: CustCustomUserDetails = custCustomUserDetailsService.loadUserByUsername(loginId) as CustCustomUserDetails

        if (!passwordEncoder.matches(loginPassword, user.loginPassword)) {
            throw NoSuchElementException("아이디 또는 비밀번호가 일치하지 않습니다.")
        }

        return UsernamePasswordAuthenticationToken(
            loginId,
            user.custId,
            user.authorities)
    }
}