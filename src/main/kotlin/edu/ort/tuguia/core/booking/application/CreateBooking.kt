package edu.ort.tuguia.core.booking.application

import javax.validation.constraints.NotBlank

class CreateBooking(
    activityId: String
) {
    @NotBlank(message = "La actividad es obligatoria")
    val activityId: String

    init {
        this.activityId = activityId
    }
}