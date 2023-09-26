package com.example.productkotlin.api.controller.ch.admin

import com.example.productkotlin.api.dto.PartnerDetailResponseDto
import com.example.productkotlin.api.dto.PartnerListResponseDto
import com.example.productkotlin.api.dto.PartnerRegisterRequestDto
import com.example.productkotlin.api.dto.comoon.RequestDto
import com.example.productkotlin.api.model.Partner
import com.example.productkotlin.api.repository.PartnerRepository
import com.example.productkotlin.api.service.NoSuchExceptionService
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/ch/partner")
class PartnerController(
    private val partnerRepository: PartnerRepository,
    private val noSuchExceptionService: NoSuchExceptionService,
) {

    /**
     * 파트너 등록
     */
    @PostMapping
    fun partnerRegister(
        @User user: CurrentCust,
        @Valid @RequestBody request: PartnerRegisterRequestDto,
    ): ResponseEntity<Any> {

        val selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())

        val requestMall = noSuchExceptionService.validateMall(requestCustId = user.custId, requestMallId = request.mallKey)

        val newData = Partner(
            partnerName = request.partnerName,
            partnerCompanyNo = request.partnerCompanyNo,
            mall = requestMall
        )

        partnerRepository.save(newData)

        return ResponseEntity.created(selfLink).build()
    }

    /**
     * 몰의 파트너 리스트 조회
     */
    @GetMapping
    fun partnerList(
        @User user: CurrentCust,
        @RequestParam(value = "mallId") requestMallId: Long,
        @Valid @ModelAttribute request: RequestDto,
    ): ResponseEntity<Any> {

        val pageRequest = PageRequest.of(request.pageNumber, request.pageSize, Sort.Direction.DESC, "registerDate")

        val requestMall = noSuchExceptionService.validateMall(requestCustId = user.custId, requestMallId = requestMallId)

        val response = partnerRepository.findByMall(requestMall, pageRequest)

        return ResponseEntity.ok(
            PartnerListResponseDto(
                pageNumber = response.number,
                pageSize = response.size,
                totalCount = response.totalElements,
                partners = response
                    .map { mallPartner ->
                        PartnerListResponseDto.Partner(
                            partnerId = mallPartner.partnerId!!,
                            partnerName = mallPartner.partnerName
                        )
                    }
                    .toList()
            )
        )
    }

    /**
     * 몰 파트너 상세내용 조히
     */
    @GetMapping("{partnerId}")
    fun partnerDetail(
        @User user: CurrentCust,
        @PathVariable(value = "partnerId") requestPartnerId: Long,
        @RequestParam(value = "mallId") requestMallId: Long,
    ): ResponseEntity<Any> {

        val requestMall = noSuchExceptionService.validateMall(requestCustId = user.custId, requestMallId = requestMallId)

        val requestMallPartner: Partner = requestMall.partners
            ?.find { it.partnerId == requestPartnerId }
            ?: throw NoSuchElementException("${requestPartnerId}에 해당하는 파트너를 찾을 수 없습니다.")

        return ResponseEntity.ok(
            PartnerDetailResponseDto(
                partnerId = requestMallPartner.partnerId!!,
                partnerName = requestMallPartner.partnerName,
                partnerCompanyNo = requestMallPartner.partnerCompanyNo,
                mallId = requestMall.mallId!!,
                mallName = requestMall.mallName,
            )
        )
    }
}