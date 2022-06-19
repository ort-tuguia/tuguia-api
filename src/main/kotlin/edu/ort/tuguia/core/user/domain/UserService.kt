package edu.ort.tuguia.core.user.domain

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.activity.domain.ActivityService
import edu.ort.tuguia.core.category.domain.CategoryService
import edu.ort.tuguia.core.review.domain.ReviewService
import edu.ort.tuguia.core.shared.Reviews
import edu.ort.tuguia.core.user.application.*
import edu.ort.tuguia.tools.helpers.http.ApiException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.context.annotation.Lazy
import java.util.*

interface UserService {
    fun saveUser(user: User): User?
    fun getUserByUsername(username: String): User
    fun getAllUsers(): List<User>
    fun deleteUserByUsername(username: String)
    fun registerUser(register: Register): User?
    fun loginUser(login: Login): User
    fun editUserPassword(username: String, changePassword: ChangePassword): User
    fun editUserDetails(username: String, userDetails: EditUser): User
    fun editUserPhoto(username: String, photoUrl: String): User
    fun editUserPhones(username: String, phones: List<UserPhone>): User
    fun editUserFavCategories(username: String, categoriesIds: List<String>): User
    fun addUserFavActivity(username: String, activityId: String): List<Activity>
    fun removeUserFavActivity(username: String, activityId: String): List<Activity>
    fun changeUserPassword(username: String, changeUserPassword: ChangeUserPassword): User
    fun asyncUpdateUserReviews(username: String)
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    @Lazy private val categoryService: CategoryService,
    @Lazy private val activityService: ActivityService,
    @Lazy private val reviewService: ReviewService
) : UserService {
    override fun saveUser(user: User): User? {
        this.userRepository.saveUser(user)
        return user
    }

    override fun getUserByUsername(username: String): User {
        return this.userRepository.getUserByUsername(username)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "El usuario $username no existe")
    }

    override fun getAllUsers(): List<User> {
        return this.userRepository.getAllUsers()
    }

    override fun deleteUserByUsername(username: String) {
        val user = this.getUserByUsername(username)

        this.userRepository.deleteUser(user)
    }

    override fun registerUser(register: Register): User? {
        val userRole = if (register.isGuide) UserRole.GUIDE else UserRole.TOURIST
        val guideIdentification = if (register.isGuide) register.guideIdentification else null
        val user = User(
            register.username,
            register.firstName,
            register.lastName,
            register.email,
            register.password,
            userRole,
            guideIdentification
        )

        val queryUser = this.userRepository.getUserByUsername(user.username)
        if (queryUser != null) {
            throw ApiException(HttpStatus.BAD_REQUEST, "El usuario ${user.username} ya se encuentra registrado")
        }

        return this.saveUser(user)
    }

    override fun loginUser(login: Login): User {
        val user = this.getUserByUsername(login.username)

        if (!user.checkPassword(login.password)) {
            throw ApiException(HttpStatus.BAD_REQUEST, "La contraseña ingresada es incorrecta")
        }

        return user
    }

    override fun editUserPassword(username: String, changePassword: ChangePassword): User {
        if (changePassword.newPassword != changePassword.confirmNewPassword) {
            throw ApiException(HttpStatus.BAD_REQUEST, "Las nuevas contraseñas no coinciden entre si")
        }

        val user =  this.getUserByUsername(username)

        if (!user.checkPassword(changePassword.currentPassword)) {
            throw ApiException(HttpStatus.BAD_REQUEST, "La contraseña actual es incorrecta")
        }

        user.changePassword(changePassword.newPassword)

        this.saveUser(user)

        return user
    }

    override fun editUserDetails(username: String, userDetails: EditUser): User {
        val user = this.getUserByUsername(username)

        user.firstName = userDetails.firstName
        user.lastName = userDetails.lastName
        user.email = userDetails.email

        this.saveUser(user)

        return user
    }

    override fun editUserPhoto(username: String, photoUrl: String): User {
        val user = this.getUserByUsername(username)

        user.photoUrl = photoUrl

        this.saveUser(user)

        return user
    }

    override fun editUserPhones(username: String, phones: List<UserPhone>): User {
        val user = this.getUserByUsername(username)

        // TODO: Add logic to add new phones, update equal phones with numbers and delete
        user.phones.clear()
        phones.forEach {
            user.phones.add(
                UserPhone(
                    UUID.randomUUID().toString(),
                    it.number,
                    it.description,
                    user
                )
            )
        }

        this.saveUser(user)

        return user
    }

    override fun editUserFavCategories(username: String, categoriesIds: List<String>): User {
        val user = this.getUserByUsername(username)

        val categories = categoriesIds.map {
            this.categoryService.getCategoryById(it)
        }

        user.favCategories = categories.toMutableList()

        this.saveUser(user)

        return user
    }

    override fun addUserFavActivity(username: String, activityId: String): List<Activity> {
        val user = this.getUserByUsername(username)

        val activitiesIds = user.favActivities.map { it.id }

        if (activitiesIds.contains(activityId)) {
            throw ApiException(HttpStatus.BAD_REQUEST, "La actividad ya fue agregada como favorita")
        }

        val activity = this.activityService.getActivityById(activityId)

        user.favActivities.add(activity)

        this.saveUser(user)

        return user.favActivities
    }

    override fun removeUserFavActivity(username: String, activityId: String): List<Activity> {
        val user = this.getUserByUsername(username)

        val activitiesIds = user.favActivities.map { it.id }

        if (!activitiesIds.contains(activityId)) {
            throw ApiException(HttpStatus.BAD_REQUEST, "La actividad ya fue removida como favorita")
        }

        val activity = this.activityService.getActivityById(activityId)

        user.favActivities.remove(activity)

        this.saveUser(user)

        return user.favActivities
    }

    override fun changeUserPassword(username: String, changeUserPassword: ChangeUserPassword): User {
        if (changeUserPassword.newPassword != changeUserPassword.confirmNewPassword) {
            throw ApiException(HttpStatus.BAD_REQUEST, "Las nuevas contraseñas no coinciden entre sí")
        }

        val user = this.getUserByUsername(username)

        user.changePassword(changeUserPassword.newPassword)

        this.saveUser(user)

        return user
    }

    override fun asyncUpdateUserReviews(username: String) {
        val user = this.getUserByUsername(username)

        if (user.role != UserRole.GUIDE) return

        val reviews = this.reviewService.getReviewsByGuide(user.username)

        var countReviews = 0
        var sumScore = 0.0

        reviews.forEach { r ->
            countReviews++
            sumScore += r.score
        }

        val avgScore: Double = if (countReviews != 0) {
            sumScore/countReviews
        } else {
            0.0
        }

        user.reviews = Reviews(avgScore, countReviews)

        this.saveUser(user)
    }
}