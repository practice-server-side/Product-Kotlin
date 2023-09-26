package com.example.productkotlin.api.service

import com.example.productkotlin.api.model.Cust
import com.example.productkotlin.api.model.Mall
import com.example.productkotlin.api.model.MallMember
import com.example.productkotlin.api.model.Product
import com.example.productkotlin.api.repository.CustRepository
import com.example.productkotlin.api.repository.MallMemberRepository
import com.example.productkotlin.api.repository.MallRepository
import com.example.productkotlin.api.repository.ProductRepository
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class NoSuchExceptionService(
    private val custRepository: CustRepository,
    private val mallRepository: MallRepository,
    private val productRepository: ProductRepository,
    private val mallMemberRepository: MallMemberRepository,
) {

    fun validateMall(requestCustId: Long, requestMallId: Long): Mall {
        val requestMall: Mall = mallRepository.findByMallId(requestMallId)
            .orElseThrow { NoSuchElementException("몰을 찾을 수 없습니다.") }

        if (requestMall.cust.custId != requestCustId) {
            throw AuthenticationException("몰을 확인할 권한이 없습니다.")
        }

        return requestMall
    }

    fun validateMall(requestCustId: Long, requestMallId: String): Mall {
        val requestMall: Mall = mallRepository.findByMallKey(requestMallId)
            .orElseThrow { NoSuchElementException("몰을 찾을 수 없습니다.") }

        if (requestMall.cust.custId != requestCustId) {
            throw AuthenticationException("몰을 확인할 권한이 없습니다.")
        }

        return requestMall
    }

    fun validateMember(
        requestMemberId: Long,
    ): MallMember {
        return mallMemberRepository.findById(requestMemberId)
            .orElseThrow { throw NoSuchElementException("회원을 찾을 수 없습니다.") }
    }

    fun validateCust(
        requestCustId: Long,
    ): Cust {
        return custRepository.findById(requestCustId)
            .orElseThrow { java.util.NoSuchElementException("회원을 찾을 수 업습니다.") }
    }

    fun validateMall(
        requestMallKey: String,
    ): Mall {
        return mallRepository.findByMallKey(requestMallKey)
            .orElseThrow { throw java.util.NoSuchElementException("존재하지 않는 몰입니다.") }
    }

    fun validateProduct(
        requestProductId: Long,
    ): Product {
        return productRepository.findById(requestProductId)
            .orElseThrow { java.util.NoSuchElementException("${requestProductId}에 해당하는 상품 정보를 찾을 수 없습니다.") }
    }

}