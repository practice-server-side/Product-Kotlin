package com.example.productkotlin.api.controller

import com.example.productkotlin.api.dto.CustJoinRequestDto
import com.example.productkotlin.api.model.Cust
import com.example.productkotlin.api.repository.CustRepository
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/cust")
class CustController (
    private val custRepository: CustRepository
){

    /**
     * 회원가입
     */
    @PostMapping
    @Transactional
    fun custJoin(
        @RequestBody request: CustJoinRequestDto
    ): ResponseEntity<Any> {

        val newData = Cust(
            loginId = request.loginId,
            loginPassword = request.loginPassword,
            custName = request.userName,
            custPhone = request.userPhone,
            custKey = UUID.randomUUID().toString())

        custRepository.save(newData)

        return ResponseEntity.ok("")
    }
}