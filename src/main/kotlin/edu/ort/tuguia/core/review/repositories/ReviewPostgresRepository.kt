package edu.ort.tuguia.core.review.repositories

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.booking.domain.Booking
import edu.ort.tuguia.core.review.domain.Review
import edu.ort.tuguia.core.review.domain.ReviewRepository
import edu.ort.tuguia.core.user.domain.User
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Join
import javax.transaction.Transactional

@Repository
@Transactional
@Profile("production|default")
class ReviewPostgresRepository : ReviewRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun createReview(review: Review) {
        em.persist(review)
    }

    override fun updateReview(review: Review) {
        em.persist(review)
    }

    override fun getReviewById(id: String): Review? {
        return em.find(Review::class.java, id)
    }

    override fun getReviewByBooking(bookingId: String): Review? {
        val query = em.criteriaBuilder.createQuery(Review::class.java)
        val from = query.from(Review::class.java)
        val booking: Join<Review, Booking> = from.join("booking")
        val select = query.select(from).where(em.criteriaBuilder.equal(booking.get<Booking>("id"), bookingId))

        return try {
            em.createQuery(select).singleResult
        } catch (ex: NoResultException) {
            null
        }
    }

    override fun getReviewsByActivity(activityId: String): List<Review> {
        val query = em.criteriaBuilder.createQuery(Review::class.java)
        val from = query.from(Review::class.java)
        val booking: Join<Review, Booking> = from.join("booking")
        val activity: Join<Booking, Activity> = booking.join("activity")
        val select = query.select(from).where(em.criteriaBuilder.equal(activity.get<Activity>("id"), activityId))

        return em.createQuery(select).resultList
    }

    override fun getReviewsByGuide(username: String): List<Review> {
        val query = em.criteriaBuilder.createQuery(Review::class.java)
        val from = query.from(Review::class.java)
        val booking: Join<Review, Booking> = from.join("booking")
        val activity: Join<Booking, Activity> = booking.join("activity")
        val guide: Join<User, Activity> = activity.join("guide")
        val select = query.select(from).where(em.criteriaBuilder.equal(guide.get<User>("username"), username))

        return em.createQuery(select).resultList
    }

    override fun deleteReview(review: Review) {
        em.remove(review)
    }
}