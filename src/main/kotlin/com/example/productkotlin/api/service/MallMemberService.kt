package com.example.productkotlin.api.service

import com.example.productkotlin.api.repository.MallMemberRepository
import org.springframework.stereotype.Service

@Service
class MallMemberService (
    private val mallMemberRepository: MallMemberRepository,
) {

}