package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Cart
import com.example.productkotlin.api.model.MallMember
import com.example.productkotlin.api.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long> {
    fun findByMember(member: MallMember): List<Cart>

    fun deleteByMemberAndProduct(member: MallMember, product: Product)
}