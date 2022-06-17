package edu.ort.tuguia.core.activity.domain

import com.fasterxml.jackson.annotation.JsonInclude
import edu.ort.tuguia.core.category.domain.Category
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "activities")
class Activity(
    id: String = "",
    name: String = "",
    description: String = "",
    locationLatitude: Double = 0.0,
    locationLongitude: Double = 0.0,
    price: Double = 0.0,
    categoryId: String = "",
    photos: MutableList<ActivityPhoto> = mutableListOf(),
    guideUsername: String = ""
) {
    @Schema(readOnly = true)
    @Id
    var id: String

    @NotBlank(message = "El nombre es obligatorio")
    var name: String

    @NotBlank(message = "La descripcion es obligatoria")
    var description: String

    var locationLatitude: Double

    var locationLongitude: Double

    @Schema(readOnly = true)
    @Transient
    var distanceKm: Double = 0.0

    @Min(0, message = "El precio debe ser como mínimo $0")
    var price: Double

    @NotBlank(message = "La categoría es obligatoria")
    var categoryId: String

    @Schema(readOnly = true)
    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var category: Category? = null

    @OneToMany(mappedBy = "activity", cascade = [CascadeType.ALL], orphanRemoval = true)
    @Valid
    var photos: MutableList<ActivityPhoto>

    @Schema(readOnly = true)
    var guideUsername: String

    @Schema(readOnly = true)
    @Embedded
    var reviews: ActivityReview? = null

    @Schema(readOnly = true)
    lateinit var createdAt: LocalDateTime

    @Schema(readOnly = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var updatedAt: LocalDateTime? = null

    init {
        this.id = id
        this.name = name
        this.description = description
        this.locationLatitude = locationLatitude
        this.locationLongitude = locationLongitude
        this.price = price
        this.categoryId = categoryId
        this.photos = photos
        this.guideUsername = guideUsername
    }
}