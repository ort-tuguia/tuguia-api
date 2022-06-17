package edu.ort.tuguia.core.review.repositories

import edu.ort.tuguia.core.review.domain.Review
import edu.ort.tuguia.core.review.domain.ReviewRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("memdb")
class ReviewInMemoryRepository : ReviewRepository {
    var reviews = mutableMapOf<String, Review>()

    override fun createReview(review: Review) {
        reviews[review.id] = review
    }

    override fun updateReview(review: Review) {
        reviews[review.id] = review
    }

    override fun getReviewById(id: String): Review? {
        return reviews[id]
    }

    override fun getReviewByBooking(bookingId: String): Review? {
        reviews.forEach { (_, u) ->
            if (u.getBookingId() == bookingId) return u
        }

        return null
    }

    override fun deleteReview(review: Review) {
        reviews.remove(review.id)
    }
}