package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.model.MallMember
import org.springframework.data.jpa.repository.JpaRepository

interface MallMemberRepository : JpaRepository<MallMember, Long>{
    fun existsByLoginIdAndMall(loginId: String, mall: Mall): Boolean
}