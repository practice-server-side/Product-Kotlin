package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Product
import com.querydsl.jpa.impl.JPAQueryFactory

class ProductRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ProductRepositoryCustom {
//    override fun findBy(): List<Product> {
//        return jpaQueryFactory.selectFrom(QProduct)
//    }
}