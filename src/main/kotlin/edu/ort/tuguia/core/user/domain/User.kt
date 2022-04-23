package edu.ort.tuguia.core.user.domain

class User(username: String, firstName: String, lastName: String, email: String) {
    // TODO: Add Json Properties
    var username: String
    var firstName: String
    var lastName: String
    var email: String

    init {
        this.username = username
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
    }
}