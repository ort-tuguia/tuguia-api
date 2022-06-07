package edu.ort.tuguia.core.activity.domain


interface ActivityRepository {
    fun createActivity(activity: Activity)
    fun updateActivity(activity: Activity)
    fun getActivityById(id: String): Activity?
    fun getAllActivities(): List<Activity>
    fun getActivitiesByUsername(username: String) : List<Activity>
    fun getActivitiesByCategories(categoriesIds: List<String>): List<Activity>
    fun deleteActivity(activity: Activity)
}