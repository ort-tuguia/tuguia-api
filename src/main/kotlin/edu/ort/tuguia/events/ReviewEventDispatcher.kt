package edu.ort.tuguia.events

import edu.ort.tuguia.core.activity.domain.ActivityService
import edu.ort.tuguia.core.review.domain.ReviewEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ReviewEventDispatcher(
    private val activityService: ActivityService
) : ApplicationListener<ReviewEvent> {
    override fun onApplicationEvent(event: ReviewEvent) {
        this.activityService.asyncUpdateActivityReviews(event.review.getActivityId())
    }
}