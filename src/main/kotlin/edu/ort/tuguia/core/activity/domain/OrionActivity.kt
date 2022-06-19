package edu.ort.tuguia.core.activity.domain

import edu.ort.tuguia.core.user.domain.User
import edu.ort.tuguia.tools.orion.OrionEntity
import java.time.LocalDateTime

class OrionActivity(
    val id: String,
    val type: String,
    val name: OrionEntity.Attr<String>,
    val description: OrionEntity.Attr<String>,
    val location: OrionEntity.Attr<String>,
    val price: OrionEntity.Attr<Double>,
    val categoryId: OrionEntity.Attr<String>,
    val photos: OrionEntity.Attr<List<ActivityPhoto>>,
    val guide: OrionEntity.Attr<User>,
    val createdAt: OrionEntity.Attr<LocalDateTime>,
    val updatedAt: OrionEntity.Attr<LocalDateTime>? = null
) {
    fun toActivity(): Activity {
        val activity = Activity()
        activity.id = id
        activity.name = name.value
        activity.description = description.value
        val locations = location.value.split(", ")
        activity.locationLatitude = locations[0].toDouble()
        activity.locationLongitude = locations[1].toDouble()
        activity.price = price.value
        activity.categoryId = categoryId.value
        activity.photos = photos.value.toMutableList()
        activity.setGuide(guide.value)
        activity.createdAt = createdAt.value
        if (updatedAt != null) {
            activity.updatedAt = updatedAt.value
        }

        return activity
    }
}