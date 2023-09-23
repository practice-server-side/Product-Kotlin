package com.example.productkotlin.api.controller

import com.example.productkotlin.api.dto.CheckLoginIdResponseDto
import com.example.productkotlin.api.dto.MallMemberRegisterRequestDto
import com.example.productkotlin.api.dto.MallMemberLoginIdCheckRequestDto
import com.example.productkotlin.api.model.MallMember
import com.example.productkotlin.api.repository.MallMemberRepository
import com.example.productkotlin.api.repository.MallRepository
import com.example.productkotlin.api.service.MallMemberAuthenticationProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
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
        @RequestBody request: MallMemberLoginIdCheckRequestDto,
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
    @PostMapping
    fun mallMemberJoin(
        @RequestBody request: MallMemberRegisterRequestDto,
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
}