package edu.ort.tuguia.core.activity.domain

import edu.ort.tuguia.tools.auth.JwtAuth
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Tag(name = "Activities")
@RestController
@RequestMapping("/api/activities")
class ActivityController(private val activityService: ActivityService) {
    @Operation(summary = "Create an activity")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createActivity(
        request: HttpServletRequest,
        @RequestBody @Valid @Parameter(description = "Activity") activity: Activity
    ): Activity {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.activityService.createActivity(loggedUser.username, activity)
    }

    @Operation(summary = "Get activity by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getActivityById(
        @PathVariable @Parameter(description = "ID of activity") id: String
    ): Activity {
        return this.activityService.getActivityById(id)
    }

    @Operation(summary = "Get all activities")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllActivities(): List<Activity> {
        return this.activityService.getAllActivities()
    }

    @Operation(summary = "Get my activities")
    @GetMapping("/myself")
    @ResponseStatus(HttpStatus.OK)
    fun getMyActivities(request: HttpServletRequest): List<Activity> {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.activityService.getMyActivities(loggedUser.username)
    }

    @Operation(summary = "Update an activity")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateActivity(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "ID of activity") id: String,
        @RequestBody @Valid @Parameter(description = "Activity") activity: Activity
    ): Activity {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        activity.id = id
        return this.activityService.updateActivity(loggedUser.username, activity)
    }

    @Operation(summary = "Delete activity by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteActivityById(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "ID of activity") id: String
    ): Activity {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.activityService.deleteActivityById(loggedUser.username, id)
    }

    @Operation(summary = "Get close activities by location and filters")
    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    fun getCloseActivitiesByLocation(
        @RequestBody @Valid @Parameter(name = "Search Options") searchOptions: ActivitySearchOptions
    ): List<Activity> {
        return this.activityService.getCloseActivities(searchOptions)
    }
}