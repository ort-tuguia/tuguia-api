package edu.ort.tuguia.events

import edu.ort.tuguia.core.activity.domain.ActivityService
import edu.ort.tuguia.core.booking.domain.BookingEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class BookingEventDispatcher(
    private val activityService: ActivityService
) : ApplicationListener<BookingEvent> {
    override fun onApplicationEvent(event: BookingEvent) {
        this.activityService.asyncUpdateActivityReviews(event.booking.getActivityId())
    }
}