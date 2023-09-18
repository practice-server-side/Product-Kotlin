package com.example.productkotlin.auth.api.service

import com.example.productkotlin.auth.api.model.Cust
import com.example.productkotlin.auth.api.model.CustomUserDetails
import com.example.productkotlin.auth.api.repository.CustRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val custRepository: CustRepository
) : UserDetailsService {

    override fun loadUserByUsername(loginId: String): UserDetails {
        val cust: Cust = custRepository.findByLoginId(loginId)
            .orElseThrow { UsernameNotFoundException(loginId + "가 존재하지 않습니다")}

        try {
            if (loginId == cust.loginId) {
                return CustomUserDetails(
                    custId = cust.custId,
                    loginId = cust.loginId,
                    loginPassword = cust.loginPassword
                )
            }
        } catch (e: Exception) {
            throw UsernameNotFoundException(loginId + "가 존재하지 않습니다")
        }

        throw UsernameNotFoundException(loginId + "가 존재하지 않습니다")
    }

}