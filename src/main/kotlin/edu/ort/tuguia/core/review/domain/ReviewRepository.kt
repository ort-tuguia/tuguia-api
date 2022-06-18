package edu.ort.tuguia.core.review.domain

interface ReviewRepository {
    fun createReview(review: Review)
    fun updateReview(review: Review)
    fun getReviewById(id: String): Review?
    fun getReviewByBooking(bookingId: String): Review?
    fun getReviewsByActivity(activityId: String): List<Review>
    fun getReviewsByGuide(username: String): List<Review>
    fun deleteReview(review: Review)
}