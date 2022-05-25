package edu.ort.tuguia.tools.orion

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class OrionClientImpl : OrionClient {
    val httpClient: HttpClient = HttpClient.newBuilder().build()
    val objectMapper = ObjectMapper()

    companion object {
        const val baseURL = "http://localhost:1026"
        const val entityEndpoint = "/v2/entities"
    }

    override fun saveEntity(entity: Map<String, Any>) {
        val requestBody: String = objectMapper.writeValueAsString(entity)

        val request = HttpRequest.newBuilder()
            .uri(URI.create(baseURL + entityEndpoint))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .header("Content-Type", "application/json")
            .build()

        httpClient.send(request, HttpResponse.BodyHandlers.ofString())
    }
}