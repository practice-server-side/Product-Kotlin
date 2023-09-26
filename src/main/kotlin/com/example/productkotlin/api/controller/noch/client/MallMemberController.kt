package com.example.productkotlin.api.controller.noch.client

import com.example.productkotlin.api.dto.*
import com.example.productkotlin.api.model.MallMember
import com.example.productkotlin.api.model.MallMemberSession
import com.example.productkotlin.api.repository.MallMemberRepository
import com.example.productkotlin.api.repository.MallMemberSessionRepository
import com.example.productkotlin.api.service.CustomAuthenticationManagerService
import com.example.productkotlin.api.service.NoSuchExceptionService
import jakarta.validation.Valid
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/noch/mallMember")
class MallMemberController(
    private val customAuthenticationManagerService: CustomAuthenticationManagerService,
    private val mallMemberSessionRepository: MallMemberSessionRepository,
    private val noSuchExceptionService: NoSuchExceptionService,
    private val mallMemberRepository: MallMemberRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    /**
     * 몰 회원 로그인 아이디 중복체크
     */
    @PostMapping("/{loginId}")
    fun checkMallMemberLoginId(
        @PathVariable(value = "loginId") requestLoginId: String,
        @Valid @RequestBody request: MallMemberLoginIdCheckRequestDto,
    ): ResponseEntity<Any> {

        val requestMall = noSuchExceptionService.validateMall(requestMallKey = request.mallKey!!)

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

        val requestMall = noSuchExceptionService.validateMall(requestMallKey = request.mallKey!!)

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

        val requestMall = noSuchExceptionService.validateMall(requestMallKey = request.mallKey!!)

        if (!mallMemberRepository.existsByLoginIdAndMall(request.loginRequestDto.loginId!!, requestMall)) {
            throw NoSuchElementException("아이디 또는 비밀번호가 일치하지 않습니다.")
        }

        val authentication = customAuthenticationManagerService.authenticate(
            UsernamePasswordAuthenticationToken(request.loginRequestDto.loginId, request.loginRequestDto.loginPassword),
            requestMall
        )

        SecurityContextHolder.getContext().authentication = authentication

        val mallMemberSession = mallMemberSessionRepository.findByMemberId(authentication.credentials as Long)

        if (mallMemberSession.isPresent) {
            mallMemberSessionRepository.deleteById(mallMemberSession.get().sessionId)
        }

        val newData = MallMemberSession(
            sessionId = UUID.randomUUID().toString(),
            memberId = authentication.credentials as Long
        )

        mallMemberSessionRepository.save(newData)

        return ResponseEntity.ok(LoginResponseDto(newData.sessionId))
    }
}