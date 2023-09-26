package com.example.productkotlin.api.model

import jakarta.persistence.*

@Entity
data class Product (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var productId: Long? = null,

    @Column(nullable = false)
    var productName: String,

    @Column(nullable = false)
    var productPrice: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partnerId")
    var partner: Partner? = null,
) : CommonDate()