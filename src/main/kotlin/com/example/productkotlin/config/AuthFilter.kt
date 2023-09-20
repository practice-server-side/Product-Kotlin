package com.example.productkotlin.config

import com.example.productkotlin.api.repository.CustSessionRepository
import com.example.productkotlin.config.dto.CurrentCust
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class AuthFilter(
    val custSessionRepository: CustSessionRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val uri = request.requestURI

        if (!uri.startsWith("/api/ch")) {
            filterChain.doFilter(request, response)
            return
        }

        val requestSessionId = request.getHeader("Authorization")

        if (requestSessionId == null) {
            throw NoSuchElementException("로그인이 필요한 요청입니다.")
        }

        val custSession = custSessionRepository.findById(requestSessionId)
            .orElseThrow { NoSuchElementException("로그인이 필요한 요청입니다.") }

        val currentCust = CurrentCust(custSession.custId)
        request.setAttribute("currentCust", currentCust)

        filterChain.doFilter(request, response)
    }

}