package edu.ort.tuguia.core.photo.domain

class Photo(photoId : String, url : String, userName : String, activityCode : String) {

    var photoId : String
    var url : String
    var userName : String
    var activityCode : String

    init {
        this.photoId = photoId
        this.url = url
        this.userName = userName
        this.activityCode =activityCode
    }

}