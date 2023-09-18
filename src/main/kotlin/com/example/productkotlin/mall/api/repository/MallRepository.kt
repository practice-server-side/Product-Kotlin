package com.example.productkotlin.mall.api.repository

import com.example.productkotlin.mall.api.model.Mall
import org.springframework.data.jpa.repository.JpaRepository

interface MallRepository : JpaRepository<Mall, Long> {

}