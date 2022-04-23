package edu.ort.tuguia.core.user.domain

import org.springframework.stereotype.Service

interface UserService {
    fun saveUser(user: User): User?
    fun getUserByUsername(username: String): User?
}

@Service
class UserServiceImpl(private val userRepository: UserRepository): UserService {

    override fun saveUser(user: User): User? {
        this.userRepository.saveUser(user)
        return user
    }

    override fun getUserByUsername(username: String): User? {
        return this.userRepository.getUserByUsername(username)
    }
}