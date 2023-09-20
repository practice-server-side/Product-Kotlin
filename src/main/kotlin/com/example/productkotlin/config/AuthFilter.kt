package com.example.productkotlin.config

import com.example.productkotlin.api.model.CustSession
import com.example.productkotlin.api.repository.CustSessionRepository
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.web.filter.OncePerRequestFilter
import java.util.NoSuchElementException

class AuthFilter(
    val custSessionRepository: CustSessionRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestSessionId = request.getHeader("Authorization")

        if (requestSessionId != null) {
            val custSession = custSessionRepository.findById(requestSessionId)
                .orElseThrow { NoSuchElementException("회원 세션이 존재하지 않습니다.")}

            val currentCust = CurrentCust(custSession.custId)
            request.setAttribute("currentCust", currentCust)
        }

        filterChain.doFilter(request, response)
    }

}