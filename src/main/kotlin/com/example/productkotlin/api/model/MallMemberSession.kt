package com.example.productkotlin.api.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("mallMemberSession")
@TypeAlias("mallMemberSession")
data class MallMemberSession (
    @Id
    var sessionId: String,
    @Indexed
    var memberId: Long,
)