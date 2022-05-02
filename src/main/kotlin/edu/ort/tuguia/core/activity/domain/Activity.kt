package edu.ort.tuguia.core.activity.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "activities")
class Activity(
    id: String = "",
    name: String = "",
    description: String = "",
    coordinates: String = "",
    price: Double = 0.0,
    guideUsername: String = ""
) {
    @Id
    var id: String

    @NotBlank(message = "El nombre es obligatorio")
    var name: String

    @NotBlank(message = "La descripcion es obligatoria")
    var description: String

    @NotBlank(message = "Las coordenadas son obligatorias")
    var coordinates: String

    @NotBlank(message = "El precio es obligatorio")
    var price: Double

    @NotBlank(message = "El guia es obligatorio")
    var guideUsername: String

    lateinit var createdAt: LocalDateTime

    var updatedAt: LocalDateTime? = null

    init {
        this.id =              id
        this.name = name
        this.description = description
        this.coordinates = coordinates
        this.price = price
        this.guideUsername = guideUsername
    }
}