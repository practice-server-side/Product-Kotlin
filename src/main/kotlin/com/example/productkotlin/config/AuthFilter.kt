package com.example.productkotlin.config

import com.example.productkotlin.api.repository.CustSessionRepository
import com.example.productkotlin.config.dto.CurrentCust
import com.example.productkotlin.config.dto.ErrorDetailsDto
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import javax.naming.AuthenticationException

class AuthFilter(
    val custSessionRepository: CustSessionRepository,
    val objectMapper: ObjectMapper,
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
            errorHandler(response, ErrorDetailsDto(status = 403, errorMessage = "로그인이 필요한 요청입니다.", timeStamp = LocalDateTime.now().toString()))
            return
        }

        val custSession = custSessionRepository.findById(requestSessionId)
            .orElseThrow { AuthenticationException("로그인이 필요한 요청입니다.") }
        //TODO : 여기도 errorHandler 보기

        val currentCust = CurrentCust(custSession.custId)
        request.setAttribute("currentCust", currentCust)

        filterChain.doFilter(request, response)
    }

    private fun errorHandler(
        response: HttpServletResponse,
        responseDto: ErrorDetailsDto
    ) {
        response.status = responseDto.status
        response.writer.write(objectMapper.writeValueAsString(responseDto))
    }


}