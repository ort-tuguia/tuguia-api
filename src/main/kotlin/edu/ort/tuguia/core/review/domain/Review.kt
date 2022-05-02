package edu.ort.tuguia.core.review.domain

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "reviews")
class Review(
    id: String = "",
    commentary: String = "",
    score: Double = 0.0,
    activityId: String = "",
    touristUsername: String = ""
) {
    @Schema(readOnly = true)
    @Id
    var id: String

    @NotBlank(message = "El comentario es obligatorio")
    var commentary: String

    @Min(0, message = "El puntaje debe ser entre 0 y 5")
    @Max(5, message = "El puntaje debe ser entre 0 y 5")
    var score: Double

    @NotBlank(message = "La actividad es obligatoria")
    var activityId: String

    @NotBlank(message = "El turista es obligatorio")
    var touristUsername: String

    @Schema(readOnly = true)
    lateinit var createdAt: LocalDateTime

    @Schema(readOnly = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var updatedAt: LocalDateTime? = null

    init {
        this.id = id
        this.commentary = commentary
        this.score = score
        this.activityId = activityId
        this.touristUsername = touristUsername
    }
}