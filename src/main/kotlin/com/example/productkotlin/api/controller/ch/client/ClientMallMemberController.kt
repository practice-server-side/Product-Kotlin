package com.example.productkotlin.api.controller.ch.client

import com.example.productkotlin.api.dto.MallMemberDetailResponseDto
import com.example.productkotlin.api.repository.MallMemberRepository
import com.example.productkotlin.api.service.MallMemberService
import com.example.productkotlin.config.annotation.Member
import com.example.productkotlin.config.dto.CurrentMember
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ch/mallMember")
class ClientMallMemberController (
    private val mallMemberRepository: MallMemberRepository,
    private val mallMemberService: MallMemberService,
) {

    /**
     * 몰 회원 상세내용 조회
     */
    @GetMapping("/{memberId}")
    fun mallMemberDetail(
        @Member member: CurrentMember,
        @PathVariable(value = "memberId") requestMemberId: Long,
    ): ResponseEntity<Any> {

        val requestMember = mallMemberService.validateMember(requestMemberId)

        return ResponseEntity.ok(
            MallMemberDetailResponseDto(
                memberId = requestMember.memberId!!,
                memberName = requestMember.memberName
            )
        )
    }
}