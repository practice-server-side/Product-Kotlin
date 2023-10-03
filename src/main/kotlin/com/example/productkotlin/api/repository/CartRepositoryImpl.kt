package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Cart
import com.example.productkotlin.api.model.MallMember
import com.example.productkotlin.api.model.QCart
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class CartRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CartRepositoryCustom{
    override fun findByCart(requestMember: MallMember, pageAble: Pageable): Page<Cart> {
        val cartList: List<Cart> = queryFactory.selectFrom(QCart.cart)
            .where(QCart.cart.member.eq(requestMember))
            .offset(pageAble.offset)
            .limit(pageAble.pageSize.toLong())
            .fetch()

        val count = queryFactory
            .select(QCart.cart.count())
            .from(QCart.cart)
            .where(QCart.cart.member.eq(requestMember))
            .fetchOne()

        return PageImpl(cartList, pageAble, count!!)
    }
}