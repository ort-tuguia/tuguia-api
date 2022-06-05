package edu.ort.tuguia.core.activity.domain

import javax.validation.constraints.Max
import javax.validation.constraints.Min

class ActivitySearchOptions(
    currentLatitude: Double,
    currentLongitude: Double,
    maxKm: Double,
    categoriesIds: List<String>,
    maxResults: Int
) {
    val currentLatitude: Double

    val currentLongitude: Double

    @Min(1, message = "El radio mínimo es 1 Km")
    @Max(1000, message = "El radio máximo es 1000 Km")
    val maxKm: Double

    val categoriesIds: List<String>

    val maxResults: Int

    init {
        this.currentLatitude = currentLatitude
        this.currentLongitude = currentLongitude
        this.maxKm = maxKm
        this.categoriesIds = categoriesIds
        this.maxResults = maxResults
    }
}