package com.example.productkotlin.config.handler

import com.example.productkotlin.config.dto.ErrorDetails
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<Any> {

        val errorMessage = ex.bindingResult.fieldErrors
            .map { it.defaultMessage }
            .joinToString(", ")

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorDetails(
                status = 400,
                errorMessage = errorMessage,
                timeStamp = LocalDateTime.now().toString()
            )
        )
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(code: String, message: String): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorDetails(
                status = 404,
                errorCode = code,
                errorMessage = message,
                timeStamp = LocalDateTime.now().toString()
            )
        )
    }
}