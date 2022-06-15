package edu.ort.tuguia.core.booking.domain

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.user.domain.User
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "bookings")
class Booking(
    id: String = "",
    tourist: User? = null,
    activity: Activity? = null
    ) {
    @Schema(readOnly = true)
    @Id
    var id: String

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    var tourist: User?

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    var activity: Activity?

    var createdAt: LocalDateTime

    init {
        this.id = id
        this.tourist = tourist
        this.activity = activity
        this.createdAt = LocalDateTime.now()
    }
}