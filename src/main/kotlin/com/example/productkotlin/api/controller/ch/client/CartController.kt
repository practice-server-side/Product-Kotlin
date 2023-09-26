package com.example.productkotlin.api.controller.ch.client

import com.example.productkotlin.api.dto.CartListResponseDto
import com.example.productkotlin.api.dto.CartRegisterRequestDto
import com.example.productkotlin.api.model.Cart
import com.example.productkotlin.api.repository.CartRepository
import com.example.productkotlin.api.repository.MallMemberRepository
import com.example.productkotlin.api.repository.ProductRepository
import com.example.productkotlin.api.service.MallMemberService
import com.example.productkotlin.config.annotation.Member
import com.example.productkotlin.config.dto.CurrentMember
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.NoSuchElementException

@RestController
@RequestMapping("/api/ch/mallMember/cart")
class CartController (
    private val cartRepository: CartRepository,
    private val mallMemberService: MallMemberService,
    private val productRepository: ProductRepository,
    private val mallMemberRepository: MallMemberRepository,
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

        val requestMember = mallMemberService.validateMember(member.memberId)

        val requestProduct = productRepository.findById(request.productId)
            .orElseThrow { NoSuchElementException("${request.productId}에 해당하는 정보를 찾을 수 없습니다.") }

        val newData = Cart(
            member = requestMember,
            product = requestProduct
        )

        cartRepository.save(newData)

        return ResponseEntity.created(selfLink).build()
    }

    /**
     * 장바구니 목록 조회하기 TODO : 페이징 처리 필요
     */
    @GetMapping
    fun cartList(
        @Member member: CurrentMember,
    ): ResponseEntity<Any> {

        val requestMember = mallMemberService.validateMember(member.memberId)

        val response = cartRepository.findByMember(requestMember)

        return ResponseEntity.ok(
            CartListResponseDto(
                carts = response.map { cart -> CartListResponseDto.Cart(productId = cart.product.productId!!) }
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

        val requestMember = mallMemberService.validateMember(member.memberId)

        val requestProduct = productRepository.findById(requestProductId)
            .orElseThrow { NoSuchElementException("${requestProductId}에 해당하는 정보를 찾을 수 없습니다.") }

        cartRepository.deleteByMemberAndProduct(requestMember, requestProduct)

        return ResponseEntity.noContent().build()
    }
}