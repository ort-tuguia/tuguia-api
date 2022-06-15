package edu.ort.tuguia.core.booking.domain

interface BookingRepository {
    fun createBooking(booking: Booking)
    fun updateBooking(booking: Booking)
    fun getBookingById(id: String): Booking?
    fun getAllBookings(): List<Booking>
    fun getBookingsByActivity(activityId: String): List<Booking>
    fun getBookingsByTourist(username: String): List<Booking>
    fun getBookingsByGuide(username: String): List<Booking>
    fun deleteBooking(booking: Booking)
}