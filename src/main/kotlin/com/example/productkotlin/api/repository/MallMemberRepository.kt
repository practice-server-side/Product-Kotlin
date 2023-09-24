package com.example.productkotlin.api.repository

import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.model.MallMember
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MallMemberRepository : JpaRepository<MallMember, Long>{
    fun existsByLoginIdAndMall(loginId: String, mall: Mall): Boolean

    fun findByLoginIdAndMall(loginId: String, mall: Mall): Optional<MallMember>
}