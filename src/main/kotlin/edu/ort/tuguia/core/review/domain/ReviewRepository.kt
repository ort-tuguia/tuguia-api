package edu.ort.tuguia.core.review.domain

interface ReviewRepository {

    fun saveReview(review : Review)
    fun getReviewById(id: String): Review?
    fun getAllReviews(): List<Review>
    fun deleteReview(review : Review)
}