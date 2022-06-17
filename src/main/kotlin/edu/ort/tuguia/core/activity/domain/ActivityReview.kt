package edu.ort.tuguia.core.activity.domain

import javax.persistence.*

@Embeddable
class ActivityReview(
    avgScore: Double = 0.0,
    reviews: Int = 0
) {
    @Column(name = "reviews_avg_score", nullable = true)
    var avgScore: Double

    @Column(name = "reviews", nullable = true)
    var reviews: Int

    init {
        this.avgScore = avgScore
        this.reviews = reviews
    }
}