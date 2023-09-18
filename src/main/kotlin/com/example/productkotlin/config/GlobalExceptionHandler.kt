package com.example.productkotlin.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleHttpMessageNotReadableException(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errorMessage = ex.bindingResult.fieldErrors
            .map { it.defaultMessage }
            .joinToString(", ")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
    }
}