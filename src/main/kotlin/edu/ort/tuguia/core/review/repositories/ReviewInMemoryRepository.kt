package edu.ort.tuguia.core.review.repositories

import edu.ort.tuguia.core.review.domain.Review
import edu.ort.tuguia.core.review.domain.ReviewRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("memdb")
class ReviewInMemoryRepository : ReviewRepository {

    var reviews : HashMap<String, Review> = HashMap()

    override fun saveReview(review : Review){

        reviews[review.id] = review
    }

    override fun getReviewById(id : String) : Review? {

        return reviews[id]
    }

    override  fun getAllReviews() : List<Review> {

        return  ArrayList(reviews.values)
    }

    override fun deleteReview(review : Review){

        reviews.remove(review.id)
    }
}