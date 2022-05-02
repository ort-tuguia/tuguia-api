package edu.ort.tuguia.core.activity.domain

import edu.ort.tuguia.tools.helpers.http.ApiException
import edu.ort.tuguia.tools.utils.DistanceCalculator
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

interface ActivityService {
    fun createActivity(activity: Activity): Activity
    fun getActivityById(id: String): Activity
    fun getAllActivities(): List<Activity>
    fun updateActivity(activity: Activity): Activity
    fun deleteActivityById(id: String): Activity
    fun getCloseActivities(
        currentLatitude: Double,
        currentLongitude: Double,
        maxKm: Double,
        maxResults: Int
    ): List<Activity> // TODO: Add categories filter
}

private const val MAX_RESULTS = 50

@Service
class ActivityServiceImpl(private val activityRepository: ActivityRepository) : ActivityService {
    override fun createActivity(activity: Activity): Activity {
        activity.id = UUID.randomUUID().toString()
        activity.createdAt = LocalDateTime.now()
        this.activityRepository.saveActivity(activity)

        return activity
    }

    override fun getActivityById(id: String): Activity {
        return this.activityRepository.getActivityById(id)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "La actividad con id $id no existe")
    }

    override fun getAllActivities(): List<Activity> {
        return this.activityRepository.getAllActivities()
    }

    override fun updateActivity(activity: Activity): Activity {
        val queryActivity = this.getActivityById(activity.id)

        queryActivity.name = activity.name
        queryActivity.description = activity.description
        queryActivity.updatedAt = LocalDateTime.now()

        this.activityRepository.saveActivity(queryActivity)

        return queryActivity
    }

    override fun deleteActivityById(id: String): Activity {
        val queryActivity = this.getActivityById(id)

        this.activityRepository.deleteActivity(queryActivity)

        return queryActivity
    }

    override fun getCloseActivities(
        currentLatitude: Double,
        currentLongitude: Double,
        maxKm: Double,
        maxResults: Int
    ): List<Activity> {
        val allActivities = this.getAllActivities()

        allActivities.forEach {
            it.distanceKm = DistanceCalculator.distance(
                currentLatitude,
                currentLongitude,
                it.locationLatitude,
                it.locationLongitude
            )
        }

        var closeActivities = allActivities.filter {
            it.distanceKm <= maxKm
        }.sortedBy { it.distanceKm }


        val maxResults = if (maxResults < MAX_RESULTS) maxResults else MAX_RESULTS

        if (closeActivities.size > maxResults) {
            closeActivities = closeActivities.slice(0 until maxResults)
        }

        return closeActivities
    }
}
