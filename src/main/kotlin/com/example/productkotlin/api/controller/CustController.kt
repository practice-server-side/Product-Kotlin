package com.example.productkotlin.api.controller

import com.example.productkotlin.api.dto.CustJoinRequestDto
import com.example.productkotlin.api.dto.CustLoginRequestDto
import com.example.productkotlin.api.model.Cust
import com.example.productkotlin.api.repository.CustRepository
import com.example.productkotlin.api.service.ApiAuthenticationProvider
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api/cust")
class CustController(
    private val authenticationManager: ApiAuthenticationProvider,
    private val custRepository: CustRepository,
    private val passwordEncoder: PasswordEncoder
) {

    /**
     * 회원가입
     */
    @PostMapping("/join")
    @Transactional
    fun custJoin(
        @RequestBody request: CustJoinRequestDto
    ): ResponseEntity<Any> {

        val selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())

        val newData = Cust(
            loginId = request.loginId,
            loginPassword = passwordEncoder.encode(request.loginPassword),
            custName = request.userName,
            custPhone = request.userPhone,
            custKey = UUID.randomUUID().toString()
        )

        custRepository.save(newData)

        return ResponseEntity.created(selfLink).build()
    }

    @PostMapping("/login")
    fun custLogin(
        @RequestBody request: CustLoginRequestDto
    ): ResponseEntity<Any> {

        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.loginId, request.loginPassword)
        )

        SecurityContextHolder.getContext().authentication = authentication

//        val newData: CustSession = CustSession.builder()
//            .sessionId(UUID.randomUUID().toString())
//            .custId(authentication.credentials as Long)
//            .build()
//
//        custSessionRepository.save(newData)

        return ResponseEntity.ok("세션 값 요청하기")
    }
}