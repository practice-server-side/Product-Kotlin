package com.example.productkotlin.api.controller

import com.example.productkotlin.api.dto.MallMemberCreateRequestDto
import com.example.productkotlin.api.model.MallMember
import com.example.productkotlin.api.repository.MallMemberRepository
import com.example.productkotlin.api.repository.MallRepository
import com.example.productkotlin.api.service.MallMemberAuthenticationProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.NoSuchElementException

@RestController
@RequestMapping("/api/noch/mallMember")
class MallMemberController(
    @Qualifier("mallMemberAuthenticationProvider")
    private val mallMemberAuthenticationProvider: MallMemberAuthenticationProvider,
    private val mallMemberRepository: MallMemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mallRepository: MallRepository,
) {

    /**
     * 몰 회원 생성
     */
    @PostMapping
    fun createMallMember(
        @RequestBody request: MallMemberCreateRequestDto,
    ): ResponseEntity<Any> {
        val selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())

        val requestMall = mallRepository.findByMallKey(request.mallKey)
            .orElseThrow {throw NoSuchElementException("존재하지 않는 몰입니다..")}

        val newData = MallMember(
            loginId = request.loginId,
            loginPassword = passwordEncoder.encode(request.loginPassword),
            memberName = request.memberName,
            mall = requestMall
        )

        mallMemberRepository.save(newData)

        return ResponseEntity.created(selfLink).build()
    }
}