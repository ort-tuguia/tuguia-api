package edu.ort.tuguia.core.shared

import org.hibernate.validator.constraints.URL

class Photo(photoUrl: String) {
    @URL(message = "Se debe ingresar una URL valida")
    val photoUrl: String

    init {
        this.photoUrl = photoUrl
    }
}