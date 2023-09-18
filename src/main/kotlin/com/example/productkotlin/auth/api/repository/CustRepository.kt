package com.example.productkotlin.auth.api.repository

import com.example.productkotlin.auth.api.model.Cust
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CustRepository : JpaRepository<Cust, Long> {
    fun findByLoginId(loginId: String): Optional<Cust>
}