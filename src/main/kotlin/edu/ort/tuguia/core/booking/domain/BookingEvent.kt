package edu.ort.tuguia.core.booking.domain

import org.springframework.context.ApplicationEvent

class BookingEvent(
    source: Any,
    val booking: Booking
) : ApplicationEvent(source)