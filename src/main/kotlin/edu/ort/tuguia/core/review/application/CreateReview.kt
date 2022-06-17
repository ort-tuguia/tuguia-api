package edu.ort.tuguia.core.review.application

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class CreateReview(
    comment: String,
    score: Double
) {
    @NotBlank(message = "El comentario es obligatorio")
    val comment: String

    @Min(0, message = "El puntaje debe ser entre 0 y 5")
    @Max(5, message = "El puntaje debe ser entre 0 y 5")
    val score: Double

    init {
        this.comment = comment
        this.score = score
    }
}