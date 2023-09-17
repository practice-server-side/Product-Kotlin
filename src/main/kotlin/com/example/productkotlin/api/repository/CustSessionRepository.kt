package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.CustSession
import org.springframework.data.repository.CrudRepository

interface CustSessionRepository : CrudRepository<CustSession, String> {
}