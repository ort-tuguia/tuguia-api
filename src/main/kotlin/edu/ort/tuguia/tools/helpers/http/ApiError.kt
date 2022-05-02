package edu.ort.tuguia.tools.helpers.http

import org.springframework.http.HttpStatus

class ApiError(val httpStatus: HttpStatus, val message: String?) {
    var errors: List<String?> = listOf()

    constructor(httpStatus: HttpStatus, message: String?, errors: List<String?>) : this(httpStatus, message) {
        this.errors = errors
    }
}