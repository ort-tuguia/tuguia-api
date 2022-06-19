package edu.ort.tuguia.core.user.application

import javax.validation.constraints.NotBlank

class ChangeUserPassword(
    newPassword: String,
    confirmNewPassword: String
) {
    @NotBlank(message = "La nueva contraseña es obligatoria")
    val newPassword: String

    @NotBlank(message = "Confirmar la nueva contraseña")
    val confirmNewPassword: String

    init {
        this.newPassword = newPassword
        this.confirmNewPassword = confirmNewPassword
    }
}