package com.example.productkotlin.api.model

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import lombok.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@NoArgsConstructor
open class CommonDate(
    @Column
    @CreatedDate
    private var registerDate: LocalDateTime? = null,
    @Column
    @LastModifiedDate
    private var updateDt: LocalDateTime? = null,
) : Serializable
