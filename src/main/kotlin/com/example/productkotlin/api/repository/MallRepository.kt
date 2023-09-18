package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Mall
import org.springframework.data.jpa.repository.JpaRepository

interface MallRepository : JpaRepository<Mall, Long> {

}