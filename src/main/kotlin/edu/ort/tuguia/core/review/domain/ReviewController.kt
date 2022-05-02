package edu.ort.tuguia.core.review.domain

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Reviews")
@RestController
@RequestMapping("/api/reviews")
class ReviewController(private val reviewService: ReviewService) {
    @Operation(summary = "Create a review")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createReview(@RequestBody @Valid @Parameter(description = "Review") review: Review): Review? {
        return this.reviewService.createReview(review)
    }

    @Operation(summary = "Get review by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getReviewById(@PathVariable @Parameter(description = "ID of review") id: String): Review? {
        return this.reviewService.getReviewById(id)
    }

    @Operation(summary = "Get all reviews")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAllReviews(): List<Review> {
        return this.reviewService.getAllReviews()
    }

    @Operation(summary = "Update a review")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateReview(
        @PathVariable @Parameter(description = "ID of review") id: String,
        @RequestBody @Valid @Parameter(description = "Review") review: Review
    ): Review? {
        review.id = id
        return this.reviewService.updateReview(review)
    }

    @Operation(summary = "Delete review by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteCategoryById(@PathVariable @Parameter(description = "ID of review") id: String): Review? {
        return this.reviewService.deleteReviewById(id)
    }
}