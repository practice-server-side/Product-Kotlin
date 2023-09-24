package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.CustSession
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CustSessionRepository : CrudRepository<CustSession, String> {
    fun findByCustId(custId: Long): Optional<CustSession>
}