package edu.ort.tuguia.core.activity.domain

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Activities")
@RestController
@RequestMapping("/api/activities")
class ActivityController(private val activityService: ActivityService) {
    @Operation(summary = "Create an activity")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createActivity(@RequestBody @Valid @Parameter(description = "Activity") activity: Activity): Activity? {
        return this.activityService.createActivity(activity)
    }

    @Operation(summary = "Get activity by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getActivityById(@PathVariable @Parameter(description = "ID of activity") id: String): Activity? {
        return this.activityService.getActivityById(id)
    }

    @Operation(summary = "Get all activities")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAllActivities(): List<Activity> {
        return this.activityService.getAllActivities()
    }

    @Operation(summary = "Update an activity")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateActivity(
        @PathVariable @Parameter(description = "ID of activity") id: String,
        @RequestBody @Valid @Parameter(description = "Activity") activity: Activity
    ): Activity? {
        activity.id = id
        return this.activityService.updateActivity(activity)
    }

    @Operation(summary = "Delete activity by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteActivityById(@PathVariable @Parameter(description = "ID of activity") id: String): Activity? {
        return this.activityService.deleteActivityById(id)
    }

    @Operation(summary = "Get close activities by location")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    fun getCloseActivitiesByLocation(
        @RequestParam @Parameter(description = "Current latitude") currentLatitude: Double,
        @RequestParam @Parameter(description = "Current longitude") currentLongitude: Double,
        @RequestParam @Parameter(description = "Maximum radius in KM") maxKm: Double,
        @RequestParam(required = false) @Parameter(description = "Max results") @Schema(defaultValue = "50") maxResults: Int
    ): List<Activity> {
        return this.activityService.getCloseActivities(currentLatitude, currentLongitude, maxKm, maxResults)
    }
}