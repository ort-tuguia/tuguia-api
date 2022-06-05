package edu.ort.tuguia.core.activity.repositories

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.activity.domain.ActivityRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("memdb")
class ActivityInMemoryRepository : ActivityRepository {
    var activities: HashMap<String, Activity> = HashMap()

    override fun createActivity(activity: Activity) {
        activities[activity.id] = activity
    }

    override fun updateActivity(activity: Activity) {
        activities[activity.id] = activity
    }

    override fun getActivityById(id: String): Activity? {
        return activities[id]
    }

    override fun getAllActivities(): List<Activity> {
        return ArrayList(activities.values)
    }

    override fun getActivitiesByCategories(categoriesIds: List<String>): List<Activity> {
        val activities = ArrayList(activities.values)
        activities.forEach {
            if (!categoriesIds.contains(it.categoryId)) {
                activities.remove(it)
            }
        }
        return activities
    }

    override fun deleteActivity(activity: Activity) {
        activities.remove(activity.id)
    }
}
