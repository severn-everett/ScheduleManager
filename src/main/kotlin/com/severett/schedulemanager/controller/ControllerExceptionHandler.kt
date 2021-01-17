package com.severett.schedulemanager.controller

import com.severett.schedulemanager.model.exception.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(value = [ResourceNotFoundException::class])
    fun handleResourceNotFound(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity<String>(ex.message, HttpStatus.NOT_FOUND)
    }
}
