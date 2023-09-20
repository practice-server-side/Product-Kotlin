package com.example.productkotlin.api.model

import com.example.productkotlin.api.model.CommonDate
import com.example.productkotlin.api.model.Cust
import jakarta.persistence.*

@Entity
data class Mall (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var mallId: Long? = null,

    @Column(nullable = false)
    var mallName: String,

    @Column(nullable = false)
    var mallKey: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custId", nullable = false)
    var cust: Cust,
) : CommonDate()