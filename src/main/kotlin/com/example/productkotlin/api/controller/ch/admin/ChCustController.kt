package com.example.productkotlin.api.controller.ch.admin

import com.example.productkotlin.api.dto.CustInfoResponseDto
import com.example.productkotlin.api.service.NoSuchExceptionService
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ch/cust")
class ChCustController (
    private val noSuchExceptionService: NoSuchExceptionService,
){
    /**
     * 회원 정보 조회
     */
    @GetMapping
    fun custInfo(@User user: CurrentCust): ResponseEntity<Any> {

        val requestCust = noSuchExceptionService.validateCust(user.custId)

        return ResponseEntity.ok(
            CustInfoResponseDto(
                custName = requestCust.custName,
                custPhone = requestCust.custPhone,
                custKey = requestCust.custKey,
            )
        )
    }
}