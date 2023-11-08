package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Cust
import com.example.productkotlin.api.model.Mall
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MallRepository : JpaRepository<Mall, Long> {
    @EntityGraph(attributePaths = ["cust"])
    fun findByMallId(mallId: Long): Optional<Mall>

    @EntityGraph(attributePaths = ["cust"])
    fun findByMallKey(mallKey: String): Optional<Mall>

    fun findByCust(cust: Cust, pageable: Pageable): Page<Mall>
}
