package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.MallMemberSession
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MallMemberSessionRepository : CrudRepository<MallMemberSession, String>{
    fun findByMemberId(memberId: Long): Optional<MallMemberSession>
}