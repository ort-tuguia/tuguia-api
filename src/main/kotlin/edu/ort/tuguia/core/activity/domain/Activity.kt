package edu.ort.tuguia.core.activity.domain

class Activity(code : String, name : String, description : String,coordinate : String , price : Float) {

    var code : String
    var name : String
    var description : String
    var coordinate : String
    var price : Float

    init {
        this.code = code
        this.name = name
        this.description = description
        this.coordinate = coordinate
        this.price = price
    }
}