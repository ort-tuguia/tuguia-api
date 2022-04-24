package edu.ort.tuguia.core.review.domain

class Review(reviewId : String, commentary : String, score : String, activityCode : String) {

    var reviewId : String
    var commentary : String
    var score : String
    var activityCode : String

    init {
        this.reviewId = reviewId
        this.commentary = commentary
        this.score = score
        this.activityCode = activityCode
    }
}