package com.example.productkotlin.api.repository

import com.example.productkotlin.api.dto.ProductListRequestDto
import com.example.productkotlin.api.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductRepositoryCustom {
    fun findByProduct(request: ProductListRequestDto, pageAble: Pageable): Page<Product>
}