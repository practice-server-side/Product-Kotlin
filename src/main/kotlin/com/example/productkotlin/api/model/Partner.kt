package com.example.productkotlin.api.model

import jakarta.persistence.*

@Entity
data class Partner (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var partnerId: Long? = null,

    @Column(nullable = false)
    var partnerName: String,

    @Column(nullable = false)
    var partnerCompanyNo: String,

    @ManyToOne
    @JoinColumn(name = "mallId", nullable = false)
    var mall: Mall,
): CommonDate()