package edu.ort.tuguia.core.user.domain

import edu.ort.tuguia.core.category.domain.CategoryService
import edu.ort.tuguia.tools.helpers.http.ApiException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

interface UserService {
    fun saveUser(user: User): User?
    fun getUserByUsername(username: String): User
    fun registerUser(register: Register): User?
    fun loginUser(login: Login): User
    fun addUserFavCategories(username: String, categoriesIds: List<String>): User
}

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val categoryService: CategoryService) : UserService {
    override fun saveUser(user: User): User? {
        this.userRepository.saveUser(user)
        return user
    }

    override fun getUserByUsername(username: String): User {
        return this.userRepository.getUserByUsername(username)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "El usuario $username no existe")
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
            throw ApiException(HttpStatus.BAD_REQUEST, "El password ingresado es incorrecto")
        }

        return user
    }

    override fun addUserFavCategories(username: String, categoriesIds: List<String>): User {
        val user = this.getUserByUsername(username)

        val categories = categoriesIds.map {
            this.categoryService.getCategoryById(it)
        }

        categories.forEach {
            if (!user.favCategories.contains(it)) {
                user.favCategories.add(it)
            }
        }

        this.saveUser(user)

        return user
    }
}