package edu.ort.tuguia.tools.orion

class OrionEntity {
    class Attr<T>(val name: String = "", val type: String = "", val value: T)

    companion object {

        const val TypeString = "String"
        const val TypeDouble = "Double"
        const val TypeTimestamp = "Timestamp"
        const val TypeGeoPoint = "geo:point"

        fun orionEntityBuilder(id: String, type: String, values: List<Attr<Any>>): Map<String, Any> {
            val entity = mutableMapOf<String, Any>(
                "id" to id,
                "type" to type
            )

            values.forEach {
                entity[it.name] = mapOf(
                    "type" to it.type,
                    "value" to it.value
                )
            }
            return entity
        }
    }
}