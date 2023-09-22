package com.example.productkotlin.api.controller

import com.example.productkotlin.api.dto.MallDetailResponseDto
import com.example.productkotlin.api.dto.MallRegisterRequestDto
import com.example.productkotlin.api.dto.MallRegisterResponseDto
import com.example.productkotlin.api.model.Cust
import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.repository.CustRepository
import com.example.productkotlin.api.repository.MallRepository
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.naming.AuthenticationException
import kotlin.NoSuchElementException

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
    fun getMallDetail(
        @User user: CurrentCust,
        @PathVariable(value = "mallId") mallId: Long,
    ): ResponseEntity<Any> {

        val requestMall: Mall = mallRepository.findByMallId(mallId)
            .orElseThrow { NoSuchElementException("몰을 찾을 수 없습니다.") }

        if (requestMall.cust.custId != user.custId) {
            throw AuthenticationException("몰을 확인할 권한이 없습니다.")
        }

        return ResponseEntity.ok(
            MallDetailResponseDto(
                mallName = requestMall.mallName,
                mallKey = requestMall.mallKey
            )
        )
    }


}