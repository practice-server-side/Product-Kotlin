package com.example.productkotlin.config.exception

import com.example.productkotlin.config.dto.ErrorDetailsDto
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import javax.naming.AuthenticationException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<Any> {

        val errorMessage = ex.bindingResult.fieldErrors
            .map { it.defaultMessage }
            .joinToString(", ")

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorDetailsDto(
                errorMessage = errorMessage,
                timeStamp = LocalDateTime.now().toString()
            )
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementException(ex: NoSuchElementException): ResponseEntity<Any> {

        val errorMessage = ex.message

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorDetailsDto(
                errorMessage = errorMessage ?: "",
                timeStamp = LocalDateTime.now().toString()
            )
        )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun authorizationServiceException(ex: AuthenticationException): ResponseEntity<Any> {

        val errorMessage = ex.message

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ErrorDetailsDto(
                errorMessage = errorMessage ?: "",
                timeStamp = LocalDateTime.now().toString()
            )
        )
    }

    @ExceptionHandler(DuplicateKeyException::class)
    fun duplicateKeyException(ex: DuplicateKeyException): ResponseEntity<Any> {

        val errorMessage = ex.message

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorDetailsDto(
                errorMessage = errorMessage ?: "",
                timeStamp = LocalDateTime.now().toString()
            )
        )
    }

}