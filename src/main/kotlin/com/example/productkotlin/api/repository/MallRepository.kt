package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Mall
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MallRepository : JpaRepository<Mall, Long> {


    @EntityGraph(attributePaths = ["cust"])
    fun findByMallId(mallId: Long): Optional<Mall>
}