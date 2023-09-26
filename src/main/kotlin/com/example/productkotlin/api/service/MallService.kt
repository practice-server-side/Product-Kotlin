package com.example.productkotlin.api.service

import com.example.productkotlin.api.repository.MallRepository
import org.springframework.stereotype.Service

@Service
class MallService (
    private val mallRepository: MallRepository,
) {


}