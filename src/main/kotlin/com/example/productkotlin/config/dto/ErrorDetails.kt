package com.example.productkotlin.config.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorDetails (
    var status: Int,
    var errorCode: String? = null,
    var errorMessage: String,
    var path: String? = null,
    var timeStamp: String,
)