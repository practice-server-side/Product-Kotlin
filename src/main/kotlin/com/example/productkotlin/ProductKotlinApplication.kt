package com.example.productkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class ProductKotlinApplication

fun main(args: Array<String>) {
	runApplication<ProductKotlinApplication>(*args)
}
