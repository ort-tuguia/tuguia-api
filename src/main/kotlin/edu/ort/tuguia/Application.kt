package edu.ort.tuguia

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@OpenAPIDefinition(info = Info(title = "TuGuia API", version = "1.0.0", description = "TuGuia API"))
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
