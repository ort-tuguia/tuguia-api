package edu.ort.tuguia.tools.orion

data class OrionAttr(val attr: String, val type: String, val value: Any)

class OrionEntity {
    companion object {
        fun orionEntityBuilder(id: String, type: String, values: List<OrionAttr>): Map<String, Any> {
            val entity = mutableMapOf<String, Any>(
                "id" to id,
                "type" to type
            )

            values.forEach {
                entity[it.attr] = mapOf(
                    "type" to it.type,
                    "value" to it.value
                )
            }
            return entity
        }
    }
}