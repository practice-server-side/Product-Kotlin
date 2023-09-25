package com.example.productkotlin.api.controller.ch.admin

import com.example.productkotlin.api.dto.MallDetailResponseDto
import com.example.productkotlin.api.dto.MallRegisterRequestDto
import com.example.productkotlin.api.dto.MallRegisterResponseDto
import com.example.productkotlin.api.model.Cust
import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.repository.CustRepository
import com.example.productkotlin.api.repository.MallRepository
import com.example.productkotlin.api.service.MallService
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/ch/mall")
class MallController (
    private val mallRepository: MallRepository,
    private val custRepository: CustRepository,
    private val mallService: MallService,
) {

    /**
     * 몰 등록
     */
    @PostMapping
    fun mallRegister(
        @User user: CurrentCust,
        @Valid @RequestBody request: MallRegisterRequestDto,
    ): ResponseEntity<Any> {

        val requestCust: Cust = custRepository.findById(user.custId)
            .orElseThrow { NoSuchElementException("회원을 찾을 수 업습니다.") }

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

    /**
     * 몰 상세내용 조회
     */
    @GetMapping("/{mallId}")
    fun mallDetail(
        @User user: CurrentCust,
        @PathVariable(value = "mallId") mallId: Long,
    ): ResponseEntity<Any> {

        val requestMall = mallService.validateMall(custId = user.custId, mallId = mallId)

        return ResponseEntity.ok(
            MallDetailResponseDto(
                mallName = requestMall.mallName,
                mallKey = requestMall.mallKey
            )
        )
    }


}