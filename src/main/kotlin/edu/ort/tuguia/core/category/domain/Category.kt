package edu.ort.tuguia.core.category.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "categories")
class Category(id: String = "", name: String = "", description: String = "") {
    @Id
    var id: String

    @NotBlank(message = "El nombre es obligatorio")
    var name: String

    @NotBlank(message = "La descripcion es obligatoria")
    var description: String

    lateinit var createdAt: LocalDateTime

    var updatedAt: LocalDateTime? = null

    init {
        this.id = id
        this.name = name
        this.description = description
    }
}