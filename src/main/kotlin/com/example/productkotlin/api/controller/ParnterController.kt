package com.example.productkotlin.api.controller

import com.example.productkotlin.api.dto.PartnerRegisterRequestDto
import com.example.productkotlin.api.model.Partner
import com.example.productkotlin.api.repository.PartnerRepository
import com.example.productkotlin.api.service.MallService
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/ch/partner")
class ParnterController (
    private val partnerRepository: PartnerRepository,
    private val mallService: MallService,
) {

    /**
     * 파트너 등록
     */
    @PostMapping
    fun partnerRegister(
        @Valid @RequestBody request: PartnerRegisterRequestDto,
        @User user: CurrentCust,
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
}