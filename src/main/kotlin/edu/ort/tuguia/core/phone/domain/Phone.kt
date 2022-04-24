package edu.ort.tuguia.core.phone.domain

class Phone(phoneId : String, number : String, description : String, userName : String) {

    var phoneId : String
    var number : String
    var description : String
    var userName : String

    init {
        this.phoneId = phoneId
        this.number = number
        this.description = description
        this.userName = userName
    }
}