package edu.ort.tuguia.core.user.application

import javax.validation.constraints.NotBlank

class ChangePassword (
    currentPassword: String,
    newPassword: String,
    confirmNewPassword: String
) {
    @NotBlank(message = "La contraseña actual es obligatoria")
    val currentPassword: String

    @NotBlank(message = "La nueva contraseña es obligatoria")
    val newPassword: String

    @NotBlank(message = "Confirmar la nueva contraseña")
    val confirmNewPassword: String

    init {
        this.currentPassword = currentPassword
        this.newPassword = newPassword
        this.confirmNewPassword = confirmNewPassword
    }
}