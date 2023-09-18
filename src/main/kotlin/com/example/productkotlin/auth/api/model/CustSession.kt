package com.example.productkotlin.auth.api.model

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias


@RedisHash
@TypeAlias("custSession")
data class CustSession (
    @Id
    var sessionId: String,
    var custId: Long,
)