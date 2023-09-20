package com.example.productkotlin.api.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.redis.core.RedisHash


@RedisHash
@TypeAlias("custSession")
data class CustSession (
    @Id
    var sessionId: String,
    var custId: Long,
)