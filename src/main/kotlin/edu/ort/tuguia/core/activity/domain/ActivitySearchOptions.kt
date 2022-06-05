package edu.ort.tuguia.core.activity.domain

data class ActivitySearchOptions(
    val currentLatitude: Double,
    val currentLongitude: Double,
    val maxKm: Double,
    val categoriesIds: List<String>,
    val maxResults: Int
)