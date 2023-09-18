package com.example.productkotlin.auth.api.service

import com.example.productkotlin.auth.api.model.CustomUserDetails
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class ApiAuthenticationProvider(
    private val customUserDetailsService: CustomUserDetailsService,
    private val passwordEncoder: PasswordEncoder,
) : AuthenticationManager {
    override fun authenticate(authentication: Authentication): Authentication {
        val loginId = authentication.name
        val loginPassword = authentication.credentials as String

        val user: CustomUserDetails = customUserDetailsService.loadUserByUsername(loginId) as CustomUserDetails
        if (!passwordEncoder.matches(loginPassword, user.loginPassword)) {
            throw IllegalAccessError()
        }

        return UsernamePasswordAuthenticationToken(
            loginId,
            user.custId,
            user.authorities)
    }
}