package com.example.productkotlin.api.repository

import com.example.productkotlin.api.dto.ProductListRequestDto
import com.example.productkotlin.api.model.Product
import com.example.productkotlin.api.model.QProduct
import com.querydsl.jpa.impl.JPAQueryFactory
import io.micrometer.common.util.StringUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ProductRepositoryCustom {
    override fun findByProduct(request: ProductListRequestDto, pageAble: Pageable): Page<Product> {

        if (StringUtils.isBlank(request.productName)) {
            request.productName = ""
        }

        val productList: List<Product> = queryFactory.selectFrom(QProduct.product)
            .where(QProduct.product.productName.like(request.productName))
            .offset(pageAble.offset)
            .limit(pageAble.pageSize.toLong())
            .fetch()

        val count = queryFactory
            .select(QProduct.product.count())
            .from(QProduct.product)
            .where(QProduct.product.productName.like(request.productName))
            .fetchOne()

        return PageImpl(productList, pageAble, count!!)
    }
}