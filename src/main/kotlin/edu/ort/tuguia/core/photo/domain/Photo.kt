package edu.ort.tuguia.core.photo.domain

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank
@Entity
@Table(name = "photos")
class Photo(id: String = "", filename: String = "") {
    @Schema(readOnly = true)
    @Id
    var id: String

    @NotBlank(message = "El nombre es obligatorio")
    var filename: String
    @Schema(readOnly = true)
    lateinit var createdAt: LocalDateTime

    @Schema(readOnly = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var updatedAt: LocalDateTime? = null

    init {
        this.id = id
        this.filename = filename
    }
}