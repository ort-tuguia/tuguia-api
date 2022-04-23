package edu.ort.tuguia.core.user.domain

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun saveUser(@RequestBody user: User): User? {
        return this.userService.saveUser(user)
    }

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): User? {
        var user = this.userService.getUserByUsername(username)

        if (user == null) {
            // TODO: Return not found
        }

        return user
    }
}