package edu.ort.tuguia.core.user.domain

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class User(username: String, firstName: String, lastName: String, email: String, password: String) {
    @NotBlank(message = "El username es obligatorio")
    var username: String

    @NotBlank(message = "El nombre es obligatorio")
    var firstName: String

    @NotBlank(message = "El apellido es obligatorio")
    var lastName: String

    @Email(message = "Se debe ingresar un email valido")
    var email: String

    @NotBlank(message = "El password es obligatorio")
    private var password: String

    init {
        this.username = username
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = password
    }
}