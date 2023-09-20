package com.example.productkotlin.api.controller

import com.example.productkotlin.api.repository.CustRepository
import com.example.productkotlin.api.dto.MallRegisterRequestDto
import com.example.productkotlin.api.dto.MallRegisterResponseDto
import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.repository.MallRepository
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.annotation.Nullable
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.NoSuchElementException
import java.util.UUID

@RestController
@RequestMapping("/api/ch/mall")
class MallController (
    private val mallRepository: MallRepository,
    private val custRepository: CustRepository,
) {

    /**
     * 몰 등록
     */
    @PostMapping
    fun createMall(
        @Valid @RequestBody request: MallRegisterRequestDto,
        @User user: CurrentCust
    ): ResponseEntity<Any> {

        val requestCust = custRepository.findById(user.custId)
            .orElseThrow { NoSuchElementException("") }

        val newData = Mall(
            mallName = request.mallName!!,
            mallKey = UUID.randomUUID().toString(),
            cust = requestCust
        )

        mallRepository.save(newData)

        return ResponseEntity.ok(
            MallRegisterResponseDto(mallId = newData.mallId)
        )
    }
}