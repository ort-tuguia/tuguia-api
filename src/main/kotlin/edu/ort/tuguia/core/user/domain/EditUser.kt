package edu.ort.tuguia.core.user.domain

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class EditUser(
    firstName: String,
    lastName: String,
    email: String,
    newPassword: String
) {
    @NotBlank(message = "El nombre es obligatorio")
    val firstName: String

    @NotBlank(message = "El apellido es obligatorio")
    val lastName: String

    @Email(message = "Se debe ingresar un email valido")
    val email: String

    @NotBlank(message = "El password es obligatorio")
    private val newPassword: String

    init {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.newPassword = newPassword
    }

    fun getNewPassword(): String {
        return this.newPassword
    }
}