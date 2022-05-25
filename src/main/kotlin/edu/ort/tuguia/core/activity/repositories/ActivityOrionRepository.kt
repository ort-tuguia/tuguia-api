package edu.ort.tuguia.core.activity.repositories

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.activity.domain.ActivityRepository
import edu.ort.tuguia.tools.orion.OrionAttr
import edu.ort.tuguia.tools.orion.OrionClient
import edu.ort.tuguia.tools.orion.OrionEntity
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("production|default")
class ActivityOrionRepository(val orionClient: OrionClient) : ActivityRepository {
    val entityType = "Activity"

    override fun saveActivity(activity: Activity) {
        val activityJSON = OrionEntity.orionEntityBuilder(activity.id, entityType, listOf(
            OrionAttr("name", "String", activity.name),
            OrionAttr("description", "String", activity.description),
            OrionAttr("location", "geo:point", "${activity.locationLatitude}, ${activity.locationLongitude}"),
            OrionAttr("price", "Float", activity.price),
            OrionAttr("guideUsername", "String", activity.guideUsername),
            // TODO: Add dates
        ))

        orionClient.saveEntity(activityJSON)
    }

    override fun getActivityById(id: String): Activity? {
        TODO("Not yet implemented")
    }

    override fun getAllActivities(): List<Activity> {
        TODO("Not yet implemented")
    }

    override fun deleteActivity(activity: Activity) {
        TODO("Not yet implemented")
    }
}