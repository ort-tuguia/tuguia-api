package edu.ort.tuguia.tools.health

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health")
@RestController
class PingController {
    @Operation(description = "Ping")
    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}