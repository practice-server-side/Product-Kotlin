package com.example.productkotlin.api.controller.ch

import com.example.productkotlin.api.dto.PartnerDetailResponseDto
import com.example.productkotlin.api.dto.PartnerListResponseDto
import com.example.productkotlin.api.dto.PartnerRegisterRequestDto
import com.example.productkotlin.api.model.Partner
import com.example.productkotlin.api.repository.PartnerRepository
import com.example.productkotlin.api.service.MallService
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.NoSuchElementException

@RestController
@RequestMapping("/api/ch/partner")
class PartnerController(
    private val partnerRepository: PartnerRepository,
    private val mallService: MallService,
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

        val requestMall = mallService.validateMall(custId = user.custId, mallKey = request.mallKey)

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
    ): ResponseEntity<Any> {

        val requestMall = mallService.validateMall(custId = user.custId, mallId = requestMallId)

        val requestMallPartners = partnerRepository.findByMall(requestMall)

        return ResponseEntity.ok(
            PartnerListResponseDto(
                partners = requestMallPartners
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

        val requestMall = mallService.validateMall(custId = user.custId, mallId = requestMallId)

        val requestMallPartner: Partner = requestMall.partners
            ?.find { it.partnerId == requestPartnerId }
            ?: throw NoSuchElementException("${requestPartnerId}에 해당하는 파트너를 찾을 수 없습니다.")

        return ResponseEntity.ok(
            PartnerDetailResponseDto(
                partnerId = requestMallPartner.partnerId!!,
                partnerName = requestMallPartner.partnerName,
                partnerCompanyNo = requestMallPartner.partnerCompanyNo,
                mallName = requestMall.mallName,
            )
        )
    }
}