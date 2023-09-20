package com.example.productkotlin.config.handler

import com.example.productkotlin.api.repository.CustSessionRepository
import com.example.productkotlin.config.AuthFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig (
    val custSessionRepository: CustSessionRepository
){
    @Bean
    fun authFilter(): FilterRegistrationBean<AuthFilter> {
        val registrationBean = FilterRegistrationBean<AuthFilter>()
        registrationBean.filter = AuthFilter(custSessionRepository)
        registrationBean.addUrlPatterns("/api/ch/*")  // 필터가 적용될 URL 패턴 지정
        return registrationBean
    }
}