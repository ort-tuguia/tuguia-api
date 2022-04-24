package edu.ort.tuguia.tools.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController {

    @GetMapping("/")
    @ResponseBody
    fun hello(): String {
        return "Hello World!"
    }

    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}