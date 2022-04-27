package edu.ort.tuguia.tools.helpers.http

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler
    fun handleApiException(ex: ApiException): ResponseEntity<ApiError> {
        val error = ApiError(ex.httpStatus, ex.message)
        return ResponseEntity(error, ex.httpStatus)
    }

    @ExceptionHandler
    fun handleGenericException(ex: Exception): ResponseEntity<ApiError> {
        val error = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        val errors = ex.bindingResult.allErrors.map {
            it.defaultMessage
        }

        val error = ApiError(HttpStatus.BAD_REQUEST, "Alguno de los argumentos no es valido", errors)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}