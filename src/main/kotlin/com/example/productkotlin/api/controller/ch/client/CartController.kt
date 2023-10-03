package com.example.productkotlin.api.controller.ch.client

import com.example.productkotlin.api.dto.CartListResponseDto
import com.example.productkotlin.api.dto.CartRegisterRequestDto
import com.example.productkotlin.api.dto.comoon.RequestDto
import com.example.productkotlin.api.model.Cart
import com.example.productkotlin.api.repository.CartRepository
import com.example.productkotlin.api.service.NoSuchExceptionService
import com.example.productkotlin.config.annotation.Member
import com.example.productkotlin.config.dto.CurrentMember
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/ch/mallMember/cart")
class CartController(
    private val cartRepository: CartRepository,
    private val noSuchExceptionService: NoSuchExceptionService,
) {

    /**
     * 장바구니 등록하기
     */
    @PostMapping
    fun cartRegister(
        @Member member: CurrentMember,
        @Valid @RequestBody request: CartRegisterRequestDto,
    ): ResponseEntity<Any> {

        val selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())

        val requestMember = noSuchExceptionService.validateMember(member.memberId)

        val requestProduct = noSuchExceptionService.validateProduct(request.productId)

        val newData = Cart(
            member = requestMember,
            product = requestProduct
        )

        cartRepository.save(newData)

        return ResponseEntity.created(selfLink).build()
    }

    /**
     * 장바구니 목록 조회하기
     */
    @GetMapping
    fun cartList(
        @Member member: CurrentMember,
        @Valid @ModelAttribute request: RequestDto
    ): ResponseEntity<Any> {

        val requestMember = noSuchExceptionService.validateMember(member.memberId)

        val pageAble: Pageable = PageRequest.of(request.pageNumber, request.pageSize)

        val response = cartRepository.findByCart(requestMember, pageAble)

        return ResponseEntity.ok(
            CartListResponseDto(
                pageNumber = response.number,
                pageSize = response.size,
                totalCount = response.totalElements,
                carts = response
                    .map { cart -> CartListResponseDto.Cart(
                        productId = cart.product.productId!!
                    ) }
                    .toList()
            )
        )
    }

    /**
     * 장바구니 삭제하기
     */
    @DeleteMapping
    @Transactional
    fun cartDelete(
        @Member member: CurrentMember,
        @RequestParam(value = "productId") requestProductId: Long,
    ): ResponseEntity<Any> {

        val requestMember = noSuchExceptionService.validateMember(member.memberId)

        val requestProduct = noSuchExceptionService.validateProduct(requestProductId)

        cartRepository.deleteByMemberAndProduct(requestMember, requestProduct)

        return ResponseEntity.noContent().build()
    }
}