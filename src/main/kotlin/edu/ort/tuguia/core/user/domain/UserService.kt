package edu.ort.tuguia.core.user.domain

import edu.ort.tuguia.tools.helpers.http.ApiException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

interface UserService {
    fun saveUser(user: User): User?
    fun getUserByUsername(username: String): User
    fun registerUser(user: User): User?
    fun loginUser(login: Login): User
}

@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService {
    override fun saveUser(user: User): User? {
        this.userRepository.saveUser(user)
        return user
    }

    override fun getUserByUsername(username: String): User {
        return this.userRepository.getUserByUsername(username)
            ?: throw ApiException(HttpStatus.NOT_FOUND, "El usuario $username no existe")
    }

    override fun registerUser(user: User): User? {
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
}