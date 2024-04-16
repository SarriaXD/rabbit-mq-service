package com.felix.rabbitmqservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {
    // Exception handler for PostServiceException, any post service exception will be dispatched to the controller,
    // and return a message to the client
    @ExceptionHandler(PostServiceException::class)
    fun handleException(ex: PostServiceException): ResponseEntity<ExceptionMessage> {
        return ResponseEntity(ex.exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}