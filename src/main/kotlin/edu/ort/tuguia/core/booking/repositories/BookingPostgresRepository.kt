package edu.ort.tuguia.core.booking.repositories

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.booking.domain.Booking
import edu.ort.tuguia.core.booking.domain.BookingRepository
import edu.ort.tuguia.core.review.domain.Review
import edu.ort.tuguia.core.user.domain.User
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Join
import javax.transaction.Transactional

@Repository
@Transactional
@Profile("production|default")
class BookingPostgresRepository : BookingRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun createBooking(booking: Booking) {
        em.persist(booking)
    }

    override fun updateBooking(booking: Booking) {
        em.persist(booking)
    }

    override fun getBookingById(id: String): Booking? {
        return em.find(Booking::class.java, id)
    }

    override fun getAllBookings(): List<Booking> {
        val query = em.criteriaBuilder.createQuery(Booking::class.java)
        val from = query.from(Booking::class.java)
        val select = query.select(from).orderBy(em.criteriaBuilder.asc(from.get<Booking>("createdAt")))

        return em.createQuery(select).resultList
    }

    override fun getBookingsByActivity(activityId: String): List<Booking> {
        // TODO: Review, filter in nested class
        val query = em.criteriaBuilder.createQuery(Booking::class.java)
        val from = query.from(Booking::class.java)
        val select = query.select(from).where(em.criteriaBuilder.equal(from.get<Booking>("activityId"), activityId))

        return em.createQuery(select).resultList
    }

    override fun getBookingsByTourist(username: String): List<Booking> {
        val query = em.criteriaBuilder.createQuery(Booking::class.java)
        val from = query.from(Booking::class.java)
        val tourist: Join<Booking, User> = from.join("tourist")
        val select = query.select(from).where(em.criteriaBuilder.equal(tourist.get<User>("username"), username))

        return em.createQuery(select).resultList
    }

    override fun getBookingsByGuide(username: String): List<Booking> {
        val query = em.criteriaBuilder.createQuery(Booking::class.java)
        val from = query.from(Booking::class.java)
        val activity: Join<Booking, User> = from.join("activity")
        val select = query.select(from).where(em.criteriaBuilder.equal(activity.get<Activity>("guideUsername"), username))

        return em.createQuery(select).resultList
    }

    override fun deleteBooking(booking: Booking) {
        em.remove(booking)
    }
}