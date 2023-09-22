package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Cust
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CustRepository : JpaRepository<Cust, Long> {
    fun findByLoginId(loginId: String): Optional<Cust>

    fun existsByLoginId(loginId: String): Boolean
}