package edu.ort.tuguia.core.phone.domain

import com.fasterxml.jackson.annotation.JsonInclude
import edu.ort.tuguia.core.user.domain.User
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "phones")
class Phone(
    id: String = "",
    number: String = "",
    description: String = "",
    user: User? = null
) {
    @Schema(readOnly = true)
    @Id
    var id: String

    @NotBlank(message = "El numero es obligatorio")
    var number: String

    @NotBlank(message = "La descripcion es obligatoria")
    var description: String

    @ManyToOne
    @JoinColumn(name = "username", nullable =  false)
    private var user: User?

    @Schema(readOnly = true)
    var createdAt: LocalDateTime

    @Schema(readOnly = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var updatedAt: LocalDateTime? = null

    init {
        this.id = id
        this.number = number
        this.description = description
        this.user = user
        this.createdAt = LocalDateTime.now()
    }
}