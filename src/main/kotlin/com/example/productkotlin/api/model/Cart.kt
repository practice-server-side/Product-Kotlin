package com.example.productkotlin.api.model

import jakarta.persistence.*

@Entity
data class Cart (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cartId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    var member: MallMember,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    var product: Product,
) : CommonDate()