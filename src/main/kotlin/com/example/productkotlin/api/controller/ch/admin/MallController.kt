package com.example.productkotlin.api.controller.ch.admin

import com.example.productkotlin.api.dto.MallDetailResponseDto
import com.example.productkotlin.api.dto.MallRegisterRequestDto
import com.example.productkotlin.api.dto.MallRegisterResponseDto
import com.example.productkotlin.api.dto.comoon.MallListResponseDto
import com.example.productkotlin.api.model.Cust
import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.repository.MallRepository
import com.example.productkotlin.api.service.NoSuchExceptionService
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/ch/mall")
class MallController(
    private val noSuchExceptionService: NoSuchExceptionService,
    private val mallRepository: MallRepository,
) {

    @GetMapping
    fun mallList(
        @User user: CurrentCust,
        @PageableDefault(size = 10, page = 0) pageable: Pageable,
    ): ResponseEntity<Any> {
        val requestCust: Cust = noSuchExceptionService.validateCust(user.custId)

        val response = mallRepository.findByCust(requestCust, pageable)

        return ResponseEntity.ok(
            MallListResponseDto(
                pageSize = response.size,
                pageNumber = response.number,
                totalCount = response.totalElements,
                malls = response
                    .map { mall ->
                        MallListResponseDto.Mall(
                            mallId = mall.mallId!!,
                            mallName = mall.mallName,
                        )
                    }
                    .toList(),
            ),
        )
    }

    /**
     * 몰 등록
     */
    @PostMapping
    fun mallRegister(
        @User user: CurrentCust,
        @Valid @RequestBody
        request: MallRegisterRequestDto,
    ): ResponseEntity<Any> {
        val requestCust: Cust = noSuchExceptionService.validateCust(user.custId)

        val newData = Mall(
            mallName = request.mallName!!,
            mallKey = UUID.randomUUID().toString(),
            cust = requestCust,
        )

        mallRepository.save(newData)

        return ResponseEntity.ok(
            MallRegisterResponseDto(mallId = newData.mallId),
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
        val requestMall = noSuchExceptionService.validateMall(requestCustId = user.custId, requestMallId = mallId)

        return ResponseEntity.ok(
            MallDetailResponseDto(
                mallName = requestMall.mallName,
                mallKey = requestMall.mallKey,
            ),
        )
    }
}
