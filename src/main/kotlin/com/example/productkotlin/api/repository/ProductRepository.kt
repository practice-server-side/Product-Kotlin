package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
}