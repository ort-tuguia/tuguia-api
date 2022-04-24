package edu.ort.tuguia.core.category.domain

class Category(categoryId : String, name : String, description : String) {

    var categoryId : String
    var name : String
    var description : String

    init {
        this.categoryId = categoryId
        this.name = name
        this.description = description
    }

}