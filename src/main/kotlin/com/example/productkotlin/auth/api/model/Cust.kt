package com.example.productkotlin.auth.api.model

import jakarta.persistence.*

@Entity
data class Cust (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var custId: Long? = null,
    @Column(nullable = false)
    var loginId: String,
    @Column(nullable = false)
    var loginPassword: String,
    @Column(nullable = false)
    var custName: String,
    @Column(nullable = false)
    var custPhone: String,
    @Column(nullable = false)
    var custKey: String,
) : CommonDate()