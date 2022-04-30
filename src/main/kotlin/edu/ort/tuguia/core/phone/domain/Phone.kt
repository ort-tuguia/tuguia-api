package edu.ort.tuguia.core.phone.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "phones")
class Phone(id : String = "" , number : String = "", description : String = "", username : String = "") {
    @Id
    var id : String
    @NotBlank(message = "El numero es obligatorio")
    var number : String

    @NotBlank(message = "La descripcion es obligatoria")
    var description : String

    @NotBlank(message = "El usuario es obligatorio")
    var username : String

    lateinit var createdAt: LocalDateTime

    var updatedAt: LocalDateTime? = null

    init {
        this.id = id
        this.number = number
        this.description = description
        this.username = username
    }
}