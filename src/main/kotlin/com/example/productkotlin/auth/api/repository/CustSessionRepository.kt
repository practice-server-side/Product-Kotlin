package com.example.productkotlin.auth.api.repository

import com.example.productkotlin.auth.api.model.CustSession
import org.springframework.data.repository.CrudRepository

interface CustSessionRepository : CrudRepository<CustSession, String> {
}