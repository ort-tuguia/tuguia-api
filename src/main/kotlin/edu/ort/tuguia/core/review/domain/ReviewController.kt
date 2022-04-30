package edu.ort.tuguia.core.review.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/reviews")
class ReviewController(private val reviewService: ReviewService) {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createReview(@RequestBody @Valid review: Review): Review? {
        return this.reviewService.createReview(review)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getReviewById(@PathVariable id: String): Review? {
        return this.reviewService.getReviewById(id)
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAllReviews(): List<Review> {
        return this.reviewService.getAllReviews()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateReview(@PathVariable id: String, @RequestBody @Valid review: Review): Review? {
        review.id = id
        return this.reviewService.updateReview(review)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteCategoryById(@PathVariable id: String): Review? {
        return this.reviewService.deleteReviewById(id)
    }
}