package com.example.productkotlin.api.controller.ch.client

import com.example.productkotlin.api.dto.LikeProductCountResponseDto
import com.example.productkotlin.api.repository.LikeProductRepository
import com.example.productkotlin.api.service.NoSuchExceptionService
import com.example.productkotlin.config.annotation.Member
import com.example.productkotlin.config.dto.CurrentMember
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ch/mallMember/like")
class LikeController (
    private val likeProductRepository: LikeProductRepository,
    private val noSuchExceptionService: NoSuchExceptionService,
    ) {

    /**
     * 회원 찜 카운트 조회
     */
    @GetMapping("/count")
    fun likeProductCount(
        @Member member: CurrentMember,
    ): ResponseEntity<Any> {

        val requestMember = noSuchExceptionService.validateMember(member.memberId)

        val response = likeProductRepository.countByMember(requestMember)

        return ResponseEntity.ok(
            LikeProductCountResponseDto(
                count = response
            )
        )
    }
}