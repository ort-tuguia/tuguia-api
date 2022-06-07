package edu.ort.tuguia.core.user.domain

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class Register(
    username: String,
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    isGuide: Boolean,
    guideIdentification: String?
) {
    @NotBlank(message = "El username es obligatorio")
    val username: String

    @NotBlank(message = "El nombre es obligatorio")
    val firstName: String

    @NotBlank(message = "El apellido es obligatorio")
    val lastName: String

    @Email(message = "El email no tiene un formato valido")
    val email: String

    @NotBlank(message = "El password es obligatorio")
    val password: String

    val isGuide: Boolean
        @JsonProperty("isGuide")
        get

    val guideIdentification: String?

    init {
        this.username = username.lowercase()
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = password
        this.isGuide = isGuide
        this.guideIdentification = guideIdentification
    }
}