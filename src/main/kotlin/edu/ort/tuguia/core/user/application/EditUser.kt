package edu.ort.tuguia.core.user.application

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class EditUser(
    firstName: String,
    lastName: String,
    email: String,
) {
    @NotBlank(message = "El nombre es obligatorio")
    val firstName: String

    @NotBlank(message = "El apellido es obligatorio")
    val lastName: String

    @Email(message = "Se debe ingresar un email valido")
    val email: String

    init {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
    }
}