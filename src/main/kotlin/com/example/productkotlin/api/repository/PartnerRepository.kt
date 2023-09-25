package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.model.Partner
import org.springframework.data.jpa.repository.JpaRepository

interface PartnerRepository : JpaRepository<Partner, Long> {
    fun findByMall(mall: Mall): List<Partner>
}