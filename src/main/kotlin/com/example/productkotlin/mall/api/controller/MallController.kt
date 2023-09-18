package com.example.productkotlin.mall.api.controller

import com.example.productkotlin.auth.api.model.Cust
import com.example.productkotlin.auth.api.repository.CustRepository
import com.example.productkotlin.mall.api.dto.MallRegisterRequestDto
import com.example.productkotlin.mall.api.dto.MallRegisterResponseDto
import com.example.productkotlin.mall.api.model.Mall
import com.example.productkotlin.mall.api.repository.MallRepository
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/mall")
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
        currentCust: Cust
    ): ResponseEntity<Any> {

        val newData = Mall(
            mallName = request.mallName!!,
            mallKey = UUID.randomUUID().toString(),
            cust = currentCust
        )

        mallRepository.save(newData)

        return ResponseEntity.ok(
            MallRegisterResponseDto(mallId = newData.mallId)
        )
    }
}