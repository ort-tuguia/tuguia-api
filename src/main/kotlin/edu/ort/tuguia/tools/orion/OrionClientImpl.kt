package edu.ort.tuguia.tools.orion

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Component
import org.springframework.http.HttpStatus
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class OrionClientImpl : OrionClient {
    val httpClient: HttpClient = HttpClient.newBuilder().build()
    val objectMapper: ObjectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(JavaTimeModule())

    companion object {
        const val baseURL = "http://localhost:1026"
        const val entityEndpoint = "/v2/entities"
    }

    override fun createEntity(entity: Map<String, Any>) {
        val requestBody: String = objectMapper.writeValueAsString(entity)

        val request = HttpRequest.newBuilder()
            .uri(URI.create(baseURL + entityEndpoint))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header("Content-Type", "application/json")
            .build()

        httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

    override fun updateEntity(id: String, entityType: String, attrs: List<OrionEntity.Attr<Any>>) {
        val attrsJSON: MutableMap<String, Any> = HashMap()
        attrs.forEach {
            attrsJSON[it.name] = mapOf(
                "type" to it.type,
                "value" to it.value
            )
        }

        val requestBody: String = objectMapper.writeValueAsString(attrsJSON)

        val request = HttpRequest.newBuilder()
            .uri(URI.create("$baseURL$entityEndpoint/$id/attrs?type=$entityType"))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header("Content-Type", "application/json")
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
            throw Exception("Entity with id $id not found")
        }

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw Exception("Error at get entity from Fiware Orion")
        }
    }

    override fun <T: Any> getEntityById(id: String, typeClass: Class<T>): T? {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$baseURL$entityEndpoint/$id"))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == HttpStatus.NOT_FOUND.value()) {
            return null
        }

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw Exception("Error at get entity from Fiware Orion")
        }

        return objectMapper.readValue(response.body(), typeClass)
    }

    override fun <T : Any> getAllEntities(entityType: String, typeClass: Class<T>): List<T> {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$baseURL$entityEndpoint?type=$entityType"))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        val listType = objectMapper.typeFactory.constructCollectionType(List::class.java, typeClass)

        return objectMapper.readValue(response.body(), listType)
    }

    override fun <T : Any> getAllEntities(entityType: String, typeClass: Class<T>, filters: List<String>): List<T> {
        var query = "q="
        filters.forEach {
            query += "$it;"
        }
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$baseURL$entityEndpoint?type=$entityType&$query"))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        val listType = objectMapper.typeFactory.constructCollectionType(List::class.java, typeClass)

        return objectMapper.readValue(response.body(), listType)
    }

    override fun deleteEntityById(id: String) {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$baseURL$entityEndpoint/$id"))
            .DELETE()
            .build()

        httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }
}