package edu.ort.tuguia.core.activity.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/activities")
class ActivityController(private val activityService: ActivityService) {
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun createActivity(@RequestBody @Valid activity: Activity): Activity? {
        return this.activityService.createActivity(activity)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getActivityById(@PathVariable id: String): Activity? {
        return this.activityService.getActivityById(id)
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    fun getAllActivities(): List<Activity> {
        return this.activityService.getAllActivities()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateActivity(@PathVariable id: String, @RequestBody @Valid activity: Activity): Activity? {
        activity.id = id
        return this.activityService.updateActivity(activity)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteActivityById(@PathVariable id: String): Activity? {
        return this.activityService.deleteActivityById(id)
    }
}