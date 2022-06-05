package edu.ort.tuguia.tools.orion

interface OrionClient {
    fun createEntity(entity: Map<String, Any>)
    fun updateEntity(id: String, entityType: String, attrs: List<OrionEntity.Attr<Any>>)
    fun <T: Any> getEntityById(id: String, typeClass: Class<T>): T?
    fun <T : Any> getAllEntities(entityType: String, typeClass: Class<T>): List<T>
    fun <T : Any> getAllEntities(entityType: String, typeClass: Class<T>, filters: List<String>): List<T>
    fun deleteEntityById(id: String)
}