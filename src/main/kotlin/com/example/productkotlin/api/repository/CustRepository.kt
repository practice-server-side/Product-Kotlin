package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Cust
import org.springframework.data.jpa.repository.JpaRepository

interface CustRepository : JpaRepository<Cust, Long> {

}