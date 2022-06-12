package edu.ort.tuguia.core.user.domain

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.shared.Photo
import edu.ort.tuguia.core.user.application.ChangePassword
import edu.ort.tuguia.core.user.application.EditUser
import edu.ort.tuguia.core.user.application.Login
import edu.ort.tuguia.core.user.application.Register
import edu.ort.tuguia.tools.auth.JwtAuth
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@Tag(name = "User")
@RestController
@RequestMapping("/api/user")
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
    fun loginUser(
        @RequestBody @Valid @Parameter(description = "Login data") login: Login,
        response: HttpServletResponse
    ): User? {
        val user = this.userService.loginUser(login)
        JwtAuth.initializeToken(user.username, user.role.toString(), response)
        return this.userService.loginUser(login)
    }

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    fun logoutUser(response: HttpServletResponse) {
        JwtAuth.clearToken(response)
    }

    @Operation(summary = "Get User Account Details")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getUserAccount(request: HttpServletRequest): User? {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.userService.getUserByUsername(loggedUser.username)
    }

    @Operation(summary = "Change User Password")
    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    fun editUserPassword(
        request: HttpServletRequest,
        @RequestBody @Valid @Parameter(description = "Change Password") changePassword: ChangePassword
    ): User {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.userService.editUserPassword(loggedUser.username, changePassword)
    }

    @Operation(summary = "Edit User Account Details")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun editUserDetails(
        request: HttpServletRequest,
        @RequestBody @Valid @Parameter(description = "User Details") userDetails: EditUser
    ): User {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.userService.editUserDetails(loggedUser.username, userDetails)
    }

    @Operation(summary = "Edit User Account Photo")
    @PutMapping("/photo")
    @ResponseStatus(HttpStatus.OK)
    fun editUserPhoto(
        request: HttpServletRequest,
        @RequestBody @Valid @Parameter(description = "Photo") photo: Photo
    ): User {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.userService.editUserPhoto(loggedUser.username, photo.photoUrl)
    }

    @Operation(summary = "Edit User Phones")
    @PutMapping("/phones")
    @ResponseStatus(HttpStatus.OK)
    fun editUserPhones(
        request: HttpServletRequest,
        @RequestBody @Valid @Parameter(description = "Phones") phones: List<UserPhone>
    ): User {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.userService.editUserPhones(loggedUser.username, phones)
    }

    @Operation(summary = "Edit User Favorite Categories")
    @PutMapping("/fav/categories")
    @ResponseStatus(HttpStatus.OK)
    fun editUserFavCategories(
        request: HttpServletRequest,
        @RequestBody @Valid @Parameter(description = "Categories ID's") categoriesIds: List<String>
    ): User {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.userService.editUserFavCategories(loggedUser.username, categoriesIds)
    }

    @Operation(summary = "Add User Favorite Activity")
    @PostMapping("/fav/activities/{activityId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun addUserFavActivity(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "Activity ID") activityId: String
    ): List<Activity> {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.userService.addUserFavActivity(loggedUser.username, activityId)
    }

    @Operation(summary = "Remove User Favorite Activity")
    @DeleteMapping("/fav/activities/{activityId}")
    @ResponseStatus(HttpStatus.OK)
    fun removeUserFavActivity(
        request: HttpServletRequest,
        @PathVariable @Parameter(description = "Activity ID") activityId: String
    ): List<Activity> {
        val loggedUser = JwtAuth.getUserFromRequest(request)
        return this.userService.removeUserFavActivity(loggedUser.username, activityId)
    }
}