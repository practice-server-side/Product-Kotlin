package com.example.productkotlin.api.service

import com.example.productkotlin.api.model.*
import com.example.productkotlin.api.repository.CustRepository
import com.example.productkotlin.api.repository.MallMemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val mallMemberRepository: MallMemberRepository,
    private val custRepository: CustRepository,
) : UserDetailsService {

    override fun loadUserByUsername(loginId: String): UserDetails {
        val cust: Cust = custRepository.findByLoginId(loginId)
            .orElseThrow { UsernameNotFoundException(loginId + "가 존재하지 않습니다")}

        try {
            if (loginId == cust.loginId) {
                return CustCustomUserDetails(
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


    fun loadUserByUsername(loginId: String, mall: Mall): UserDetails {
        val mallMember: MallMember = mallMemberRepository.findByLoginIdAndMall(loginId, mall)
            .orElseThrow { UsernameNotFoundException(loginId + "가 존재하지 않습니다") }

        try {
            if (loginId == mallMember.loginId) {
                return MallMemberCustomUserDetails(
                    memberId = mallMember.memberId,
                    loginId = mallMember.loginId,
                    loginPassword = mallMember.loginPassword
                )
            }
        } catch (e: Exception) {
            throw UsernameNotFoundException(loginId + "가 존재하지 않습니다")
        }

        throw UsernameNotFoundException(loginId + "가 존재하지 않습니다")
    }

}