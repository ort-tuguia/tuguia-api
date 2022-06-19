package edu.ort.tuguia.core.user.domain

import edu.ort.tuguia.core.user.application.ChangeUserPassword
import edu.ort.tuguia.tools.auth.JwtAuth
import edu.ort.tuguia.tools.helpers.http.ApiException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

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

    @Operation(summary = "Change user password")
    @PutMapping("/{username}/password")
    @ResponseStatus(HttpStatus.OK)
    fun changeUserPassword(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "Username to change password") username: String,
        @RequestBody @Valid @Parameter(description = "Change User Password") changePassword: ChangeUserPassword
    ) : User {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        if (!loggedUser.hasRole(UserRole.ADMIN.toString())) {
            throw ApiException(HttpStatus.UNAUTHORIZED, "Solo los administradores pueden acceder a este recurso")
        }
        return this.userService.changeUserPassword(username, changePassword)
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