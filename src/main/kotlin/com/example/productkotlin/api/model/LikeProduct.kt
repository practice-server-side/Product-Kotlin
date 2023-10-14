package com.example.productkotlin.api.model

import jakarta.persistence.*

@Entity
data class LikeProduct (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val likeProductId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    val member: MallMember,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    val product: Product,
) : CommonDate()