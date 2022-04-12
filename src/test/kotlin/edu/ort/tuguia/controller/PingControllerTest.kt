package edu.ort.tuguia.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@WebMvcTest
class PingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `ping should return pong`() {
        mockMvc.perform(get("/ping")).andExpect {
            status().isOk
            content().string("pong")
        }
    }
}