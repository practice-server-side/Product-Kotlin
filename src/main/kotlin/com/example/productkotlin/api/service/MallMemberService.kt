package com.example.productkotlin.api.service

import com.example.productkotlin.api.model.MallMember
import com.example.productkotlin.api.repository.MallMemberRepository
import org.springframework.stereotype.Service

@Service
class MallMemberService (
    private val mallMemberRepository: MallMemberRepository,
) {

    fun validateMember(
        requestMemberId: Long,
    ): MallMember {
        return mallMemberRepository.findById(requestMemberId)
            .orElseThrow { throw NoSuchElementException("회원을 찾을 수 없습니다.") }
    }
}