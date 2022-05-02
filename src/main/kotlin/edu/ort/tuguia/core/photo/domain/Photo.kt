package edu.ort.tuguia.core.photo.domain

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

class Photo(id: String, url: String) {
    @Schema(readOnly = true)
    var id: String

    @NotBlank(message = "La url es obligatoria")
    var url: String

    init {
        this.id = id
        this.url = url
    }
}