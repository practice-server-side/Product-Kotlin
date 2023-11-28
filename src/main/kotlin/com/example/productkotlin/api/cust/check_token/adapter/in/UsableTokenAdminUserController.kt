package com.example.productkotlin.api.cust.check_token.adapter.`in`

import com.example.productkotlin.api.cust.check_token.application.port.`in`.GetAdminUserDetailUseCase
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/ch/cust")
@RestController
class UsableTokenAdminUserController(
    private val getAdminUserDetailUseCase: GetAdminUserDetailUseCase,
) {
    @GetMapping
    fun custInfo(@User user: CurrentCust): ResponseEntity<Any> {
        return ResponseEntity.ok(
            getAdminUserDetailUseCase.getAdminUserDetail(currentCustId = user.custId).toResponse(),
        )
    }
}
