package com.example.productkotlin.api.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class MallMember (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var memberId: Long? = null,

    @Column
    var memberName: String,

    @ManyToOne
    @JoinColumn(name = "mallId")
    val mall: Mall
) : CommonDate()