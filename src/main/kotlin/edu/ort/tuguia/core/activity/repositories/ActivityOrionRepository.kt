package edu.ort.tuguia.core.activity.repositories

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.activity.domain.ActivityRepository
import edu.ort.tuguia.core.activity.domain.OrionActivity
import edu.ort.tuguia.tools.orion.OrionClient
import edu.ort.tuguia.tools.orion.OrionEntity
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("default")
class ActivityOrionRepository(val orionClient: OrionClient) : ActivityRepository {
    val entityType = "Activity"

    override fun createActivity(activity: Activity) {
        val activityJSON = OrionEntity.orionEntityBuilder(
            activity.id, entityType, mutableListOf(
                OrionEntity.Attr("name", OrionEntity.TypeString, activity.name),
                OrionEntity.Attr("description", OrionEntity.TypeString, activity.description),
                OrionEntity.Attr("location", OrionEntity.TypeGeoPoint, "${activity.locationLatitude}, ${activity.locationLongitude}"),
                OrionEntity.Attr("price", OrionEntity.TypeDouble, activity.price),
                OrionEntity.Attr("categoryId", OrionEntity.TypeString, activity.categoryId),
                OrionEntity.Attr("guideUsername", OrionEntity.TypeString, activity.guideUsername),
                OrionEntity.Attr("createdAt", OrionEntity.TypeTimestamp, activity.createdAt.toString())
            )
        )

        orionClient.createEntity(activityJSON)
    }

    override fun updateActivity(activity: Activity) {
        orionClient.updateEntity(activity.id, entityType, listOf(
            OrionEntity.Attr("name", OrionEntity.TypeString, activity.name),
            OrionEntity.Attr("description", OrionEntity.TypeString, activity.description),
            OrionEntity.Attr("location", OrionEntity.TypeGeoPoint, "${activity.locationLatitude}, ${activity.locationLongitude}"),
            OrionEntity.Attr("price", OrionEntity.TypeDouble, activity.price),
            OrionEntity.Attr("categoryId", OrionEntity.TypeString, activity.categoryId),
            OrionEntity.Attr("updatedAt", OrionEntity.TypeTimestamp, activity.updatedAt.toString())
        ))
    }

    override fun getActivityById(id: String): Activity? {
        val orionActivity = orionClient.getEntityById(id, OrionActivity::class.java) ?: return null

        return orionActivity.toActivity()
    }

    override fun getAllActivities(): List<Activity> {
        val orionActivities = orionClient.getAllEntities(entityType, OrionActivity::class.java)

        return orionActivities.map {
            it.toActivity()
        }
    }

    override fun getActivitiesByUsername(username: String): List<Activity> {
        val orionActivities = orionClient.getAllEntities(entityType, OrionActivity::class.java, listOf(
            "guideUsername:$username"
        ))

        return orionActivities.map {
            it.toActivity()
        }
    }

    override fun getActivitiesByCategories(categoriesIds: List<String>): List<Activity> {
        val categories = categoriesIds.joinToString(separator = ",")
        val orionActivities = orionClient.getAllEntities(entityType, OrionActivity::class.java, listOf(
            "categoryId:$categories"
        ))

        return orionActivities.map {
            it.toActivity()
        }
    }

    override fun deleteActivity(activity: Activity) {
        return orionClient.deleteEntityById(activity.id)
    }
}