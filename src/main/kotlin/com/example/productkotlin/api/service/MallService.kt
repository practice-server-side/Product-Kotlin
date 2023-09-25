package com.example.productkotlin.api.service

import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.repository.MallRepository
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class MallService (
    private val mallRepository: MallRepository,
) {

    fun validateMall(custId: Long, mallId: Long): Mall {
        val requestMall: Mall = mallRepository.findByMallId(mallId)
            .orElseThrow { NoSuchElementException("몰을 찾을 수 없습니다.") }

        if (requestMall.cust.custId != custId) {
            throw AuthenticationException("몰을 확인할 권한이 없습니다.")
        }

        return requestMall
    }

    fun validateMall(custId: Long, mallKey: String): Mall {
        val requestMall: Mall = mallRepository.findByMallKey(mallKey)
            .orElseThrow { NoSuchElementException("몰을 찾을 수 없습니다.") }

        if (requestMall.cust.custId != custId) {
            throw AuthenticationException("몰을 확인할 권한이 없습니다.")
        }

        return requestMall
    }
}