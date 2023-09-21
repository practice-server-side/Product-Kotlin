package com.example.productkotlin.config

import com.example.productkotlin.api.repository.CustSessionRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig (
    val custSessionRepository: CustSessionRepository,
    val objectMapper: ObjectMapper,
){
    @Bean
    fun authFilter(): FilterRegistrationBean<AuthFilter> {
        val registrationBean = FilterRegistrationBean<AuthFilter>()
        registrationBean.filter = AuthFilter(custSessionRepository, objectMapper)
        registrationBean.addUrlPatterns("/api/ch/*")  // 필터가 적용될 URL 패턴 지정
        return registrationBean
    }
}