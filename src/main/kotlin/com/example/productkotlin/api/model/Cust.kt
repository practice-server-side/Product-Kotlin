package com.example.productkotlin.api.model

import jakarta.persistence.*
import lombok.Builder

@Entity
@Builder
data class Cust (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var custId: Long? = null,
    @Column
    private var loginId: String,
    @Column
    private var loginPassword: String,
    @Column
    private var custName: String,
    @Column
    private var custPhone: String,
    @Column
    private var custKey: String,
) : CommonDate()