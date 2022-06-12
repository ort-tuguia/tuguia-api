package edu.ort.tuguia.core.user.domain

import edu.ort.tuguia.tools.auth.JwtAuth
import edu.ort.tuguia.tools.helpers.http.ApiException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@Tag(name = "Users")
@RestController
@RequestMapping("/api/users")
class UsersController(private val userService: UserService) {
    @Operation(summary = "Get all users")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllUsers(request: HttpServletRequest): List<User> {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        if (!loggedUser.hasRole(UserRole.ADMIN.toString())) {
            throw ApiException(HttpStatus.UNAUTHORIZED, "Solo los administradores pueden acceder a este recurso")
        }
        return this.userService.getAllUsers()
    }

    @Operation(summary = "Delete user by username")
    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteUserByUsername(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "Username to delete") username: String
    ) {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        if (!loggedUser.hasRole(UserRole.ADMIN.toString())) {
            throw ApiException(HttpStatus.UNAUTHORIZED, "Solo los administradores pueden acceder a este recurso")
        }
        this.userService.deleteUserByUsername(username)
    }
}