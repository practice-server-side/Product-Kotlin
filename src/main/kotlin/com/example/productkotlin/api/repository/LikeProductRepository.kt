package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.LikeProduct
import com.example.productkotlin.api.model.MallMember
import org.springframework.data.jpa.repository.JpaRepository

interface LikeProductRepository : JpaRepository<LikeProduct, Long> {
    fun countByMember(member: MallMember): Int
}