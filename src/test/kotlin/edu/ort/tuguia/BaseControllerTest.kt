package edu.ort.tuguia

import edu.ort.tuguia.core.user.domain.UserRepository
import edu.ort.tuguia.core.user.domain.UserService
import org.springframework.boot.test.mock.mockito.MockBean

abstract class BaseControllerTest {
    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var userService: UserService
}