package edu.ort.tuguia.core.review.domain

import edu.ort.tuguia.tools.helpers.http.ApiException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

interface ReviewService {
    fun createReview(review: Review): Review
    fun getReviewById(id: String): Review
    fun getAllReviews(): List<Review>
    fun updateReview(review: Review): Review
    fun deleteReviewById(id: String): Review

}

@Service
class ReviewServiceImp(private val reviewRepository: ReviewRepository) : ReviewService {
    override fun createReview(review: Review): Review {

        review.id = UUID.randomUUID().toString()
        review.createdAt = LocalDateTime.now()
        this.reviewRepository.saveReview(review)

        return review
    }

    override fun getReviewById(id: String): Review {

        return this.reviewRepository.getReviewById(id)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "La review con id $id no existe")
    }

    override fun getAllReviews(): List<Review> {

        return this.reviewRepository.getAllReviews()
    }

    override fun updateReview(review: Review): Review {

        val queryReview = this.getReviewById(review.id)

        queryReview.commentary = review.commentary
        queryReview.score = review.score
        queryReview.activityId = review.activityId
        queryReview.touristUsername = review.touristUsername
        queryReview.createdAt = LocalDateTime.now()
        queryReview.updatedAt = LocalDateTime.now()

        this.reviewRepository.saveReview(queryReview)

        return queryReview

    }

    override fun deleteReviewById(id: String): Review {

        val review = this.getReviewById(id)
        this.reviewRepository.deleteReview(review)

        return review
    }
}