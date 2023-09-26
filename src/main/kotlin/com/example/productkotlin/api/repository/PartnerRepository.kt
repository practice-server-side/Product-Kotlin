package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.model.Partner
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository

interface PartnerRepository : JpaRepository<Partner, Long> {
    fun findByMall(mall: Mall): List<Partner>

    fun findByMall(mall: Mall, pageRequest: PageRequest): Page<Partner>
}