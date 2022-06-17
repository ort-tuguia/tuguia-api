package edu.ort.tuguia.core.booking.domain

import edu.ort.tuguia.core.activity.domain.ActivityService
import edu.ort.tuguia.core.review.application.CreateReview
import edu.ort.tuguia.core.review.domain.Review
import edu.ort.tuguia.core.review.domain.ReviewService
import edu.ort.tuguia.core.user.domain.UserRole
import edu.ort.tuguia.core.user.domain.UserService
import edu.ort.tuguia.tools.helpers.http.ApiException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

interface BookingService {
    fun createBooking(username: String, activityId: String): Booking
    fun getBookingById(id: String): Booking
    fun getAllBookings(): List<Booking>
    fun getMyBookings(username: String): List<Booking>
    fun deleteBookingById(username: String, id: String): Booking
    fun addBookingReview(username: String, bookingId: String, review: CreateReview): Review
    fun editBookingReview(username: String, bookingId: String, review: Review): Review
    fun getBookingReview(bookingId: String): Review
}

@Service
class BookingServiceImpl(
    private val bookingRepository: BookingRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val userService: UserService,
    private val activityService: ActivityService,
    private val reviewService: ReviewService
) : BookingService {
    override fun createBooking(username: String, activityId: String): Booking {
        val newBooking = Booking(
            UUID.randomUUID().toString(),
            this.userService.getUserByUsername(username),
            this.activityService.getActivityById(activityId)
        )

        this.bookingRepository.createBooking(newBooking)

        this.eventPublisher.publishEvent(BookingEvent(this, newBooking))

        return newBooking
    }

    override fun getBookingById(id: String): Booking {
        return bookingRepository.getBookingById(id)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "La reserva con id $id no existe")
    }

    override fun getAllBookings(): List<Booking> {
        return this.bookingRepository.getAllBookings()
    }

    override fun getMyBookings(username: String): List<Booking> {
        val user = this.userService.getUserByUsername(username)

        return when (user.role) {
            UserRole.TOURIST -> this.bookingRepository.getBookingsByTourist(username)
            UserRole.GUIDE -> this.bookingRepository.getBookingsByGuide(username)
            else -> {
                listOf() // TODO: null list for now, bad request should be returned
            }
        }
    }

    override fun deleteBookingById(username: String, id: String): Booking {
        val queryBooking = this.getBookingById(id)

        // TODO: Validate guide
        if (queryBooking.tourist!!.username != username) {
            throw ApiException(HttpStatus.UNAUTHORIZED, "No es posible eliminar la reserva ya que pertenece a otro usuario")
        }

        this.bookingRepository.deleteBooking(queryBooking)

        this.eventPublisher.publishEvent(BookingEvent(this, queryBooking))

        return queryBooking
    }

    override fun addBookingReview(username: String, bookingId: String, review: CreateReview): Review {
        val user = this.userService.getUserByUsername(username)
        val booking = this.getBookingById(bookingId)

        if (booking.review != null) {
            throw ApiException(HttpStatus.BAD_REQUEST, "Ya existe una reseña para la reserva actual")
        }

        if (user.username != (booking.tourist?.username ?: "")) {
            throw ApiException(HttpStatus.UNAUTHORIZED, "La reseña solo puede ser realizada por el usuario que creó la reserva")
        }

        return this.reviewService.createReview(user.username, booking, review)
    }

    override fun editBookingReview(username: String, bookingId: String, review: Review): Review {
        val user = this.userService.getUserByUsername(username)
        val booking = this.getBookingById(bookingId)

        if (booking.review == null) {
            throw ApiException(HttpStatus.NOT_FOUND, "Aún no hay una reseña realizada para la reserva actual")
        }

        review.id = booking.review!!.id

        return this.reviewService.updateReview(review)
    }

    override fun getBookingReview(bookingId: String): Review {
        // TODO: Validate username?
        return this.reviewService.getReviewByBooking(bookingId)
    }
}