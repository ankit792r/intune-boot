package com.x64tech.intune.http

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.naming.AuthenticationException

@RestController
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity(
            ApiResponse(success = false, data = null, message = e.message ?: "Unknown error", error = "INTERNAL_ERROR"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNotFound(ex: NoHandlerFoundException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse<Nothing>(
            success = false,
            message = "Endpoint not found: ${ex.requestURL}",
            error = "NOT_FOUND"
        )
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse<Nothing>(
            success = false,
            message = "Access denied",
            error = "ACCESS_DENIED"
        )
        return ResponseEntity(response, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthentication(ex: AuthenticationException): ResponseEntity<ApiResponse<Nothing>> {
        val response = ApiResponse<Nothing>(
            success = false,
            message = "Authentication required",
            error = "UNAUTHORIZED"
        )
        return ResponseEntity(response, HttpStatus.UNAUTHORIZED)
    }
}