package edu.ort.tuguia.core.user.domain

import edu.ort.tuguia.tools.auth.JwtAuth
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
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
    fun loginUser(@RequestBody @Valid @Parameter(description = "Login data") login: Login, response: HttpServletResponse): User? {
        val user = this.userService.loginUser(login)
        JwtAuth.initializeToken(user.username, response)
        return this.userService.loginUser(login)
    }

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    fun logoutUser(response: HttpServletResponse) {
        JwtAuth.clearToken(response)
    }

    @Operation(summary = "Get User Details")
    @PostMapping("/details")
    @ResponseStatus(HttpStatus.OK)
    fun getUserDetails(request: HttpServletRequest): User? {
        val username = JwtAuth.getUsernameFromRequest(request)
        return this.userService.getUserByUsername(username)
    }
}