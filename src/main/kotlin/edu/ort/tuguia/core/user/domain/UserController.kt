package edu.ort.tuguia.core.user.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody @Valid user: User): User? {
        return this.userService.registerUser(user)
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserByUsername(@PathVariable username: String): User? {
        return userService.getUserByUsername(username)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun loginUser(@RequestBody @Valid login: Login): User? {
        return this.userService.loginUser(login)
    }
}