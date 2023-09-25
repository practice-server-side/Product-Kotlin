package com.example.productkotlin.api.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

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
)