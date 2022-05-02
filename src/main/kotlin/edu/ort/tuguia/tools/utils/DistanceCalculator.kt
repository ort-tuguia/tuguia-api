package edu.ort.tuguia.tools.utils

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class DistanceCalculator {
    companion object {
        /**
         * @param fromLat is the latitude of initial location
         * @param fromLong is the longitude of initial location
         * @param toLat is the latitude of final location
         * @param toLong is the longitude of final location
         * @return the distance in KM
         */
        fun distance(fromLat: Double, fromLong: Double, toLat: Double, toLong: Double): Double {
            if ((fromLat == toLat) && (fromLong == toLong)) {
                return 0.0
            }

            val theta = fromLong - toLong
            var dist = sin(Math.toRadians(fromLat)) * sin(Math.toRadians(toLat)) + cos(Math.toRadians(fromLat)) * cos(Math.toRadians(toLat)) * cos(Math.toRadians(theta))

            dist = acos(dist)
            dist = Math.toDegrees(dist)
            dist *= 60 * 1.1515 * 1.609344

            return dist
        }
    }
}