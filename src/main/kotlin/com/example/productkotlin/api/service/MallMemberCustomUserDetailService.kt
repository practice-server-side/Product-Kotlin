package com.example.productkotlin.api.service

import com.example.productkotlin.api.repository.MallMemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MallMemberCustomUserDetailService (
    private val mallMemberRepository: MallMemberRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("Not yet implemented")
    }

}