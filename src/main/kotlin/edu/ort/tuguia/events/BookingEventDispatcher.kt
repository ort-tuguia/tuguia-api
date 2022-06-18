package edu.ort.tuguia.events

import edu.ort.tuguia.core.activity.domain.ActivityService
import edu.ort.tuguia.core.booking.domain.BookingEvent
import edu.ort.tuguia.core.user.domain.UserService
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class BookingEventDispatcher(
    private val activityService: ActivityService,
    private val userService: UserService
) : ApplicationListener<BookingEvent> {
    override fun onApplicationEvent(event: BookingEvent) {
        this.activityService.asyncUpdateActivityReviews(event.booking.getActivityId())
        this.userService.asyncUpdateUserReviews(event.booking.getGuideUsername())
    }
}