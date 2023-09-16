package com.example.productkotlin.api.model

import jakarta.persistence.*

@Entity
data class Cust (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var custId: Long? = null,
    @Column
    var loginId: String,
    @Column
    var loginPassword: String,
    @Column
    var custName: String,
    @Column
    var custPhone: String,
    @Column
    var custKey: String,
) : CommonDate()