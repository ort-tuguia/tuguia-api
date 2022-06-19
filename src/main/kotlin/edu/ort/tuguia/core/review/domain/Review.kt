package edu.ort.tuguia.core.review.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import edu.ort.tuguia.core.booking.domain.Booking
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "reviews")
class Review(
    id: String = "",
    comment: String = "",
    score: Double = 0.0,
    booking: Booking = Booking()
) {
    @Schema(readOnly = true)
    @Id
    var id: String

    @NotBlank(message = "El comentario es obligatorio")
    var comment: String

    @Min(0, message = "El puntaje debe ser entre 0 y 5")
    @Max(5, message = "El puntaje debe ser entre 0 y 5")
    var score: Double

    @Schema(readOnly = true)
    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id", nullable = false)
    private var booking: Booking

    @Schema(readOnly = true)
    var createdAt: LocalDateTime

    @Schema(readOnly = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var updatedAt: LocalDateTime? = null

    init {
        this.id = id
        this.comment = comment
        this.score = score
        this.booking = booking
        this.createdAt = LocalDateTime.now()
    }

    @JsonIgnore
    fun getBookingId(): String {
        return booking.id
    }

    fun getActivityId(): String {
        return booking.activity.id
    }

    fun getGuideUsername(): String {
        return booking.getGuideUsername()
    }
}