package edu.ort.tuguia.tools.helpers.http

import org.springframework.http.HttpStatus

class ApiException(httpStatus: HttpStatus, message: String?) : RuntimeException(message) {
    val httpStatus: HttpStatus

    init {
        this.httpStatus = httpStatus
    }
}