package edu.ort.tuguia.core.user.repositories

import edu.ort.tuguia.core.user.domain.User
import edu.ort.tuguia.core.user.domain.UserRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryRepository: UserRepository {

    var users: HashMap<String, User> = HashMap()

    override fun saveUser(user: User) {
        users[user.username] = user
    }

    override fun getUserByUsername(username: String): User? {
        return users[username]
    }
}