package edu.ort.tuguia.core.user.domain

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users_phones")
class UserPhone(
    id: String = "",
    number: String = "",
    description: String = "",
    user: User? = null
) {
    @Schema(readOnly = true)
    @Id
    var id: String

    @NotBlank(message = "El número es obligatorio")
    var number: String

    @NotBlank(message = "La descripción es obligatoria")
    var description: String

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private var user: User?

    @Schema(readOnly = true)
    var createdAt: LocalDateTime

    init {
        this.id = id
        this.number = number
        this.description = description
        this.user = user
        this.createdAt = LocalDateTime.now()
    }
}