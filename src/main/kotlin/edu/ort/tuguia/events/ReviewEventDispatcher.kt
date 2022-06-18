package edu.ort.tuguia.events

import edu.ort.tuguia.core.activity.domain.ActivityService
import edu.ort.tuguia.core.review.domain.ReviewEvent
import edu.ort.tuguia.core.user.domain.UserService
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ReviewEventDispatcher(
    private val activityService: ActivityService,
    private val userService: UserService
) : ApplicationListener<ReviewEvent> {
    override fun onApplicationEvent(event: ReviewEvent) {
        this.activityService.asyncUpdateActivityReviews(event.review.getActivityId())
        this.userService.asyncUpdateUserReviews(event.review.getGuideUsername())
    }
}