package edu.ort.tuguia.core.booking.domain

import edu.ort.tuguia.core.booking.application.CreateBooking
import edu.ort.tuguia.core.review.application.CreateReview
import edu.ort.tuguia.core.review.domain.Review
import edu.ort.tuguia.tools.auth.JwtAuth
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Tag(name = "Bookings")
@RestController
@RequestMapping("/api/bookings")
class BookingController(private val bookingService: BookingService) {
    @Operation(summary = "Create activity booking")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBooking(
        request: HttpServletRequest,
        @RequestBody @Valid @Parameter(description = "Booking") booking: CreateBooking
    ) : Booking {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.bookingService.createBooking(loggedUser.username, booking.activityId)
    }

    @Operation(summary = "Get booking by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getBookingById(
        @PathVariable @Parameter(description = "ID of booking") id: String
    ): Booking {
        return this.bookingService.getBookingById(id)
    }

    @Operation(summary = "Get all bookings")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllBookings(): List<Booking> {
        return this.bookingService.getAllBookings()
    }

    @Operation(summary = "Get my bookings")
    @GetMapping("/myself")
    @ResponseStatus(HttpStatus.OK)
    fun getMyBookings(request: HttpServletRequest): List<Booking> {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.bookingService.getMyBookings(loggedUser.username)
    }

    @Operation(summary = "Delete booking by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteBookingById(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "ID of booking") id: String
    ) : Booking {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.bookingService.deleteBookingById(loggedUser.username, id)
    }

    @Operation(summary = "Add booking review")
    @PostMapping("/{id}/review")
    @ResponseStatus(HttpStatus.CREATED)
    fun createBookingReview(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "ID of booking") id: String,
        @RequestBody @Valid @Parameter(description = "Booking Review") review: CreateReview
    ) : Review {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.bookingService.addBookingReview(loggedUser.username, id, review)
    }

    @Operation(summary = "Edit booking review")
    @PutMapping("/{id}/review")
    @ResponseStatus(HttpStatus.OK)
    fun editBookingReview(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "ID of booking") id: String,
        @RequestBody @Valid @Parameter(description = "Booking Review") review: Review
    ) : Review {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.bookingService.editBookingReview(loggedUser.username, id, review)
    }

    @Operation(summary = "Get booking review")
    @GetMapping("/{id}/review")
    @ResponseStatus(HttpStatus.OK)
    fun getBookingReview(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "ID of booking") id: String
    ) : Review {
        return this.bookingService.getBookingReview(id)
    }
}