package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Cart
import com.example.productkotlin.api.model.MallMember
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CartRepositoryCustom {
    fun findByCart(requestMember: MallMember, pageAble: Pageable): Page<Cart>
}