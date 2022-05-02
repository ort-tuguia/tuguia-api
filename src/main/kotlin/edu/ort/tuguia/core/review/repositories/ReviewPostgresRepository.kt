package edu.ort.tuguia.core.review.repositories

import edu.ort.tuguia.core.review.domain.Review
import edu.ort.tuguia.core.review.domain.ReviewRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
@Transactional
@Profile("production|default")
class ReviewPostgresRepository : ReviewRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun saveReview(review: Review) {

        em.persist(review)
    }

    override fun getReviewById(id: String): Review? {

        return em.find(Review::class.java, id)
    }

    override fun getAllReviews(): List<Review> {

        val query = em.criteriaBuilder.createQuery(Review::class.java)
        val from = query.from(Review::class.java)
        val select = query.select(from).orderBy(em.criteriaBuilder.asc(from.get<Review>("createdAt")))

        return em.createQuery(select).resultList
    }

    override fun deleteReview(review: Review) {

        em.remove(review)
    }
}