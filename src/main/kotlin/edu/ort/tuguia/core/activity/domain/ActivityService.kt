package edu.ort.tuguia.core.activity.domain

import edu.ort.tuguia.core.category.domain.CategoryService
import edu.ort.tuguia.core.review.domain.Review
import edu.ort.tuguia.core.review.domain.ReviewService
import edu.ort.tuguia.core.shared.Reviews
import edu.ort.tuguia.core.user.domain.UserService
import edu.ort.tuguia.tools.helpers.http.ApiException
import edu.ort.tuguia.tools.utils.DistanceCalculator
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

interface ActivityService {
    fun createActivity(username: String, activity: Activity): Activity
    fun getActivityById(id: String): Activity
    fun getAllActivities(): List<Activity>
    fun getMyActivities(username: String): List<Activity>
    fun getActivitiesByCategories(categoriesIds: List<String>): List<Activity>
    fun updateActivity(username: String, activity: Activity): Activity
    fun deleteActivityById(username: String, id: String): Activity
    fun getCloseActivities(searchOptions: ActivitySearchOptions): List<Activity>
    fun getActivityReviews(activityId: String): List<Review>
    fun asyncUpdateActivityReviews(activityId: String)
}

private const val MAX_RESULTS = 50

@Service
class ActivityServiceImpl(
    private val activityRepository: ActivityRepository,
    private val categoryService: CategoryService,
    private val userService: UserService,
    private val reviewService: ReviewService
) : ActivityService {
    override fun createActivity(username: String, activity: Activity): Activity {
        val user = this.userService.getUserByUsername(username)

        activity.id = UUID.randomUUID().toString()
        activity.category = this.categoryService.getCategoryById(activity.categoryId)
        activity.setGuide(user)
        activity.createdAt = LocalDateTime.now()

        activity.photos.forEach {
            it.id = UUID.randomUUID().toString()
            it.setActivity(activity)
        }

        this.activityRepository.createActivity(activity)

        return activity
    }

    override fun getActivityById(id: String): Activity {
        val activity = this.activityRepository.getActivityById(id)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "La actividad con id $id no existe")

        activity.category = this.categoryService.getCategoryById(activity.categoryId)

        return activity
    }

    override fun getAllActivities(): List<Activity> {
        return this.activityRepository.getAllActivities()
    }

    override fun getMyActivities(username: String): List<Activity> {
        return this.activityRepository.getActivitiesByUsername(username)
    }

    override fun getActivitiesByCategories(categoriesIds: List<String>): List<Activity> {
        return this.activityRepository.getActivitiesByCategories(categoriesIds)
    }

    override fun updateActivity(username: String, activity: Activity): Activity {
        val queryActivity = this.getActivityById(activity.id)

        if (queryActivity.getGuideUsername() != username) {
            throw ApiException(HttpStatus.UNAUTHORIZED, "No es posible modificar la actividad ya que pertenece a otro usuario")
        }

        queryActivity.name = activity.name
        queryActivity.description = activity.description
        queryActivity.locationLatitude = activity.locationLatitude
        queryActivity.locationLongitude = activity.locationLongitude
        queryActivity.price = activity.price

        if (queryActivity.categoryId != activity.categoryId) {
            queryActivity.categoryId = activity.categoryId
            queryActivity.category = this.categoryService.getCategoryById(activity.categoryId)
        }

        if (activity.photos.isNotEmpty()) {
            queryActivity.photos.clear()
            activity.photos.forEach {
                queryActivity.photos.add(
                    ActivityPhoto(
                        UUID.randomUUID().toString(),
                        it.photoUrl,
                        queryActivity
                    )
                )
            }
        }

        queryActivity.updatedAt = LocalDateTime.now()

        this.activityRepository.updateActivity(queryActivity)

        return queryActivity
    }

    override fun deleteActivityById(username: String, id: String): Activity {
        val queryActivity = this.getActivityById(id)

        if (queryActivity.getGuideUsername() != username) {
            throw ApiException(HttpStatus.UNAUTHORIZED, "No es posible eliminar la actividad ya que pertenece a otro usuario")
        }

        if (queryActivity.isDeleted) {
            throw ApiException(HttpStatus.BAD_REQUEST, "La actividad ya se encuentra eliminada")
        }

        queryActivity.isDeleted = true
        queryActivity.deletedAt = LocalDateTime.now()

        this.activityRepository.updateActivity(queryActivity)

        return queryActivity
    }

    override fun getCloseActivities(searchOptions: ActivitySearchOptions): List<Activity> {
        val allActivities: List<Activity> = if (searchOptions.categoriesIds.isEmpty())
            this.getAllActivities()
        else {
            this.getActivitiesByCategories(searchOptions.categoriesIds)
        }

        allActivities.forEach {
            it.distanceKm = DistanceCalculator.distance(
                searchOptions.currentLatitude,
                searchOptions.currentLongitude,
                it.locationLatitude,
                it.locationLongitude
            )
        }

        var closeActivities = allActivities.filter {
            it.distanceKm <= searchOptions.maxKm
        }.sortedBy { it.distanceKm }


        val maxResults = if (searchOptions.maxResults < MAX_RESULTS) searchOptions.maxResults else MAX_RESULTS

        if (closeActivities.size > maxResults) {
            closeActivities = closeActivities.slice(0 until maxResults)
        }

        return closeActivities
    }

    override fun getActivityReviews(activityId: String): List<Review> {
        return this.reviewService.getReviewsByActivity(activityId)
    }

    override fun asyncUpdateActivityReviews(activityId: String) {
        val activity = this.getActivityById(activityId)
        val reviews = this.reviewService.getReviewsByActivity(activity.id)

        var countReviews = 0
        var sumScore = 0.0

        reviews.forEach { r ->
            countReviews++
            sumScore += r.score
        }

        val avgScore: Double = if (countReviews != 0) {
            sumScore/countReviews
        } else {
            0.0
        }

        activity.reviews = Reviews(avgScore, countReviews)

        this.activityRepository.updateActivity(activity)
    }
}
