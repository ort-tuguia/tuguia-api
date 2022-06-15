package edu.ort.tuguia.core.booking.repositories

import edu.ort.tuguia.core.booking.domain.Booking
import edu.ort.tuguia.core.booking.domain.BookingRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("memdb")
class BookingInMemoryRepository : BookingRepository {
    var bookings: HashMap<String, Booking> = HashMap()

    override fun createBooking(booking: Booking) {
        bookings[booking.id] = booking
    }

    override fun updateBooking(booking: Booking) {
        bookings[booking.id] = booking
    }

    override fun getBookingById(id: String): Booking? {
        return bookings[id]
    }

    override fun getAllBookings(): List<Booking> {
        return ArrayList(bookings.values)
    }

    override fun getBookingsByActivity(activityId: String): List<Booking> {
        return ArrayList(bookings.values).filter {
            it.activity!!.id == activityId
        }
    }

    override fun getBookingsByTourist(username: String): List<Booking> {
        return ArrayList(bookings.values).filter {
            it.tourist!!.username == username
        }
    }

    override fun getBookingsByGuide(username: String): List<Booking> {
        return ArrayList(bookings.values).filter {
            it.activity!!.guideUsername == username
        }
    }

    override fun deleteBooking(booking: Booking) {
        bookings.remove(booking.id)
    }
}