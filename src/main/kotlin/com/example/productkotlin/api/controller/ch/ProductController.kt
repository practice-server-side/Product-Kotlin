package com.example.productkotlin.api.controller.ch

import com.example.productkotlin.api.dto.ProductRegisterRequestDto
import com.example.productkotlin.api.model.Partner
import com.example.productkotlin.api.model.Product
import com.example.productkotlin.api.repository.ProductRepository
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
import java.util.NoSuchElementException

@RestController
@RequestMapping("/api/ch/product")
class ProductController (
    private val mallService: MallService,
    private val productRepository: ProductRepository,
){

    /**
     * 상품 등록
     */
    @PostMapping
    fun productRegister(
        @User user: CurrentCust,
        @Valid @RequestBody request: ProductRegisterRequestDto,
    ): ResponseEntity<Any> {

        val selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())

        val requestMall = mallService.validateMall(custId = user.custId, mallId = request.mallId)

        val requestMallPartner: Partner = requestMall.partners
            ?.find { it.partnerId == request.partnerId }
            ?: throw NoSuchElementException("${request.partnerId}에 해당하는 파트너를 찾을 수 없습니다.")

        val newData = Product(
            productName = request.productName,
            productPrice = request.productPrice,
            partner = requestMallPartner
        )

        productRepository.save(newData)

        return ResponseEntity.created(selfLink).build()
    }

}