package com.example.productkotlin.api.controller.ch.admin

import com.example.productkotlin.api.dto.ProductDetailResponseDto
import com.example.productkotlin.api.dto.ProductListRequestDto
import com.example.productkotlin.api.dto.ProductListResponseDto
import com.example.productkotlin.api.dto.ProductRegisterRequestDto
import com.example.productkotlin.api.model.Partner
import com.example.productkotlin.api.model.Product
import com.example.productkotlin.api.repository.ProductRepository
import com.example.productkotlin.api.service.MallService
import com.example.productkotlin.api.service.NoSuchExceptionService
import com.example.productkotlin.config.annotation.User
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/ch/product")
class ProductController(
    private val mallService: MallService,
    private val productRepository: ProductRepository,
    private val noSuchExceptionService: NoSuchExceptionService,
) {

    /**
     * 상품 등록
     */
    @PostMapping
    fun productRegister(
        @User user: CurrentCust,
        @Valid @RequestBody
        request: ProductRegisterRequestDto,
    ): ResponseEntity<Any> {
        val selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())

        val requestMall = noSuchExceptionService.validateMall(requestCustId = user.custId, requestMallId = request.mallId)

        val requestMallPartner: Partner = requestMall.partners
            ?.find { it.partnerId == request.partnerId }
            ?: throw NoSuchElementException("${request.partnerId}에 해당하는 파트너를 찾을 수 없습니다.")

        val newData = Product(
            productName = request.productName,
            productPrice = request.productPrice,
            partner = requestMallPartner,
            stock = request.stock,
        )

        productRepository.save(newData)

        return ResponseEntity.created(selfLink).build()
    }

    /**
     * 상품 리스트 조회
     */
    @GetMapping
    fun productList(
        @User uesr: CurrentCust,
        @Valid @ModelAttribute
        request: ProductListRequestDto,
    ): ResponseEntity<Any> {
        val pageAble: Pageable = PageRequest.of(request.pageNumber, request.pageSize)

        val response = productRepository.findByProduct(request, pageAble)

        return ResponseEntity.ok(
            ProductListResponseDto(
                pageSize = response.size,
                pageNumber = response.number,
                totalCount = response.totalElements,
                products = response
                    .map { product ->
                        ProductListResponseDto.Product(
                            productId = product.productId!!,
                            productName = product.productName,
                            productPrice = product.productPrice,
                            stock = product.stock,
                        )
                    }
                    .toList(),
            ),
        )
    }

    /**
     * 상품 상세내용 조회
     */
    @GetMapping("/{productId}")
    fun productDetail(
        @User user: CurrentCust,
        @PathVariable(value = "productId") requestProductId: Long,
    ): ResponseEntity<Any> {
        val requestProduct = productRepository.findById(requestProductId)
            .orElseThrow { throw NoSuchElementException("${requestProductId}에 해당하는 정보를 찾을 수 업습니다.") }

        return ResponseEntity.ok(
            ProductDetailResponseDto(
                productId = requestProduct.productId!!,
                productName = requestProduct.productName,
                productPrice = requestProduct.productPrice,
                partnerName = requestProduct.partner!!.partnerName,
                partnerId = requestProduct.partner!!.partnerId!!,
            ),
        )
    }
}
