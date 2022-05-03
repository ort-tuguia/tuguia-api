package edu.ort.tuguia.core.user.domain

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Users")
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    @Operation(summary = "Register")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody @Valid @Parameter(description = "User data") register: Register): User? {
        return this.userService.registerUser(register)
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun loginUser(@RequestBody @Valid @Parameter(description = "Login data") login: Login): User? {
        return this.userService.loginUser(login)
    }
}