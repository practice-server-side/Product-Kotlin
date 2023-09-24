package com.example.productkotlin.api.controller

import com.example.productkotlin.api.dto.*
import com.example.productkotlin.api.model.MallMember
import com.example.productkotlin.api.repository.MallMemberRepository
import com.example.productkotlin.api.repository.MallRepository
import com.example.productkotlin.api.service.MallMemberAuthenticationProvider
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
     * 몰 회원 로그인 아이디 중복체크
     */
    @PostMapping("/{loginId}")
    fun checkMallMemberLoginId(
        @PathVariable(value = "loginId") requestLoginId: String,
        @Valid @RequestBody request: MallMemberLoginIdCheckRequestDto,
    ): ResponseEntity<Any> {

        val requestMall = mallRepository.findByMallKey(request.mallKey!!)
            .orElseThrow { throw NoSuchElementException("존재하지 않는 몰입니다.") }

        return ResponseEntity.ok(
            CheckLoginIdResponseDto(mallMemberRepository.existsByLoginIdAndMall(requestLoginId, requestMall))
        )
    }

    /**
     * 몰 회원가입
     */
    @PostMapping("/join")
    fun mallMemberJoin(
        @Valid @RequestBody request: MallMemberJoinRequestDto,
    ): ResponseEntity<Any> {

        val selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())

        val requestMall = mallRepository.findByMallKey(request.mallKey!!)
            .orElseThrow {throw NoSuchElementException("존재하지 않는 몰입니다.")}

        if (mallMemberRepository.existsByLoginIdAndMall(request.loginId!!, requestMall)) {
            throw DuplicateKeyException("이미 사용중인 아이디 입니다.")
        }

        val newData = MallMember(
            loginId = request.loginId,
            loginPassword = passwordEncoder.encode(request.loginPassword!!),
            memberName = request.memberName!!,
            mall = requestMall
        )

        mallMemberRepository.save(newData)

        return ResponseEntity.created(selfLink).build()
    }

    /**
     * 몰 회원 로그인
     */
    @GetMapping("/login")
    fun mallMemberLogin(
        @Valid @RequestBody request: MallMemberLoginRequestDto
    ): ResponseEntity<Any> {

        val requestMall = mallRepository.findByMallKey(request.mallKey)
            .orElseThrow { throw NoSuchElementException("존재하지 않는 몰입니다.") }

        if (!mallMemberRepository.existsByLoginIdAndMall(request.loginRequestDto.loginId!!, requestMall)) {
            throw NoSuchElementException("아이디 또는 비밀번호가 일치하지 않습니다.")
        }

        val authentication = mallMemberAuthenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken(request.loginRequestDto.loginId, request.loginRequestDto.loginPassword),
            requestMall
        )

        SecurityContextHolder.getContext().authentication = authentication




        return ResponseEntity.ok(
            ""
        )
    }
}