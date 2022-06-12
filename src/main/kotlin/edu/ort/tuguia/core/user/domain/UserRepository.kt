package edu.ort.tuguia.core.user.domain

interface UserRepository {
    fun saveUser(user: User)
    fun getUserByUsername(username: String): User?
    fun getAllUsers(): List<User>
    fun deleteUser(user: User)
}