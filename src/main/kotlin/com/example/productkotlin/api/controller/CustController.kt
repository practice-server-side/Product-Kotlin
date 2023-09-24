package com.example.productkotlin.api.controller

import com.example.productkotlin.api.dto.CheckLoginIdResponseDto
import com.example.productkotlin.api.dto.CustJoinRequestDto
import com.example.productkotlin.api.dto.LoginRequestDto
import com.example.productkotlin.api.dto.CustLoginResponseDto
import com.example.productkotlin.api.model.Cust
import com.example.productkotlin.api.model.CustSession
import com.example.productkotlin.api.repository.CustRepository
import com.example.productkotlin.api.repository.CustSessionRepository
import com.example.productkotlin.api.service.CustAuthenticationProvider
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/noch/cust")
class CustController(
    @Qualifier("custAuthenticationProvider")
    private val custAuthenticationProvider: CustAuthenticationProvider,
    private val custRepository: CustRepository,
    private val passwordEncoder: PasswordEncoder,
    private val custSessionRepository: CustSessionRepository,
) {

    /**
     * 회원 로그인 아이디 중복체크
     */
    @GetMapping("/{loginId}")
    fun checkCustLoginId(
        @PathVariable(value = "loginId") requestLoginId: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(
            CheckLoginIdResponseDto(exists = custRepository.existsByLoginId(requestLoginId))
        )
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    @Transactional
    fun custJoin(
        @Valid @RequestBody request: CustJoinRequestDto
    ): ResponseEntity<Any> {

        val selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())

        if (custRepository.existsByLoginId(request.loginId!!)) {
            throw DuplicateKeyException("이미 사용중인 아이디 입니다.")
        }

        val newData = Cust(
            loginId = request.loginId,
            loginPassword = passwordEncoder.encode(request.loginPassword!!),
            custName = request.userName!!,
            custPhone = request.userPhone!!,
            custKey = UUID.randomUUID().toString()
         )

        custRepository.save(newData)

        return ResponseEntity.created(selfLink).build()
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    fun custLogin(
        @Valid @RequestBody request: LoginRequestDto
    ): ResponseEntity<Any> {

        if (!custRepository.existsByLoginId(request.loginId!!)) {
            throw NoSuchElementException("아이디 또는 비밀번호가 일치하지 않습니다.")
        }

        val authentication = custAuthenticationProvider.authenticate(
            UsernamePasswordAuthenticationToken(request.loginId, request.loginPassword)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val custSession = custSessionRepository.findByCustId(authentication.credentials as Long)

        if (custSession.isPresent) {
            custSessionRepository.deleteById(custSession.get().sessionId)
        }

        val newData = CustSession(
            sessionId = UUID.randomUUID().toString(),
            custId = authentication.credentials as Long
        )

        custSessionRepository.save(newData)

        return ResponseEntity.ok(CustLoginResponseDto(sessionId = newData.sessionId))
    }
}