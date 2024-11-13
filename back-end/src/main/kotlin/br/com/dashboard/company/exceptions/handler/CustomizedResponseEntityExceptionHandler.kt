package br.com.dashboard.company.exceptions.handler

import br.com.dashboard.company.exceptions.DuplicateNameException
import br.com.dashboard.company.exceptions.ForbiddenActionRequestException
import br.com.dashboard.company.exceptions.InvalidJwtAuthenticationException
import br.com.dashboard.company.exceptions.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
class CustomizedResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(InvalidJwtAuthenticationException::class)
    fun handleInvalidJwtAuthenticationExceptions(
        exception: Exception,
        request: WebRequest,
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.FORBIDDEN.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(ForbiddenActionRequestException::class)
    fun handleForbiddenActionRequestExceptions(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.FORBIDDEN.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DuplicateNameException::class)
    fun handleDuplicateNameException(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            status = HttpStatus.CONFLICT.value(),
            message = exception.message
        )
        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT)
    }
}
