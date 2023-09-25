package com.example.productkotlin.api.model

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mall")
    var partners: List<Partner>? = null
) : CommonDate()