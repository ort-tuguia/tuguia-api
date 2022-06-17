package edu.ort.tuguia.core.review.domain

import edu.ort.tuguia.core.booking.domain.Booking
import edu.ort.tuguia.core.review.application.CreateReview
import edu.ort.tuguia.tools.helpers.http.ApiException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

interface ReviewService {
    fun createReview(username: String, booking: Booking, review: CreateReview): Review
    fun getReviewById(id: String): Review
    fun getReviewByBooking(bookingId: String): Review
    fun getReviewsByActivity(activityId: String): List<Review>
    fun updateReview(review: Review): Review
    fun deleteReviewById(id: String): Review
}

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository
) : ReviewService {
    override fun createReview(username: String, booking: Booking, review: CreateReview): Review {
        val newReview = Review(
            UUID.randomUUID().toString(),
            review.comment,
            review.score,
            booking
        )

        this.reviewRepository.createReview(newReview)

        return newReview
    }

    override fun getReviewById(id: String): Review {
        return this.reviewRepository.getReviewById(id)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "La reseña con id $id no existe")
    }

    override fun getReviewByBooking(bookingId: String): Review {
        return this.reviewRepository.getReviewByBooking(bookingId)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "La reserva con id $bookingId no tiene una reseña aún")
    }

    override fun getReviewsByActivity(activityId: String): List<Review> {
        TODO("Not yet implemented")
    }

    override fun updateReview(review: Review): Review {
        val queryReview = this.getReviewById(review.id)

        queryReview.comment = review.comment
        queryReview.score = review.score
        queryReview.updatedAt = LocalDateTime.now()

        this.reviewRepository.updateReview(queryReview)

        return queryReview
    }

    override fun deleteReviewById(id: String): Review {
        val queryReview = this.getReviewById(id)

        this.reviewRepository.deleteReview(queryReview)

        return queryReview
    }

}