package edu.ort.tuguia.core.user.application

import javax.validation.constraints.NotBlank

class Login(username: String, password: String) {
    @NotBlank(message = "El username es obligatorio")
    val username: String

    @NotBlank(message = "El password es obligatorio")
    val password: String

    init {
        this.username = username.lowercase()
        this.password = password
    }
}