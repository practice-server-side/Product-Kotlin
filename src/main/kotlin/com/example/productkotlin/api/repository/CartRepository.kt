package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Cart
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long> {
}