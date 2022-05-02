package edu.ort.tuguia.core.photo.domain

import javax.validation.constraints.NotBlank

class Photo(id: String, url: String) {

    var id: String

    @NotBlank(message = "La url es obligatoria")
    var url: String

    init {
        this.id = id
        this.url = url

    }
}