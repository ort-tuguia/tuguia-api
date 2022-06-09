package edu.ort.tuguia.core.user.application

import javax.validation.constraints.NotBlank

class ChangePassword (
    currentPassword: String,
    newPassword: String,
    confirmNewPassword: String
) {
    @NotBlank(message = "La clave actual es obligatoria")
    val currentPassword: String

    @NotBlank(message = "La nueva clave es obligatoria")
    val newPassword: String

    @NotBlank(message = "Confirmar la nueva clave")
    val confirmNewPassword: String

    init {
        this.currentPassword = currentPassword
        this.newPassword = newPassword
        this.confirmNewPassword = confirmNewPassword
    }
}