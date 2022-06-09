package edu.ort.tuguia.core.activity.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.URL
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "activities_photos")
class ActivityPhoto(
    id: String = "",
    photoUrl: String = "",
    activity: Activity? = null
) {
    @Schema(readOnly = true)
    @Id
    var id: String

    @URL(message = "Se debe ingresar una URL valida")
    var photoUrl: String

    @Schema(readOnly = true)
    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private var activity: Activity?

    init {
        this.id = id
        this.photoUrl = photoUrl
        this.activity = activity
    }

    fun setActivity(activity: Activity) {
        this.activity = activity
    }
}