package com.example.productkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProductKotlinApplication

fun main(args: Array<String>) {
	runApplication<ProductKotlinApplication>(*args)
}
