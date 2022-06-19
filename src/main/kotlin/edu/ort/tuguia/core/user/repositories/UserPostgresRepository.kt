package edu.ort.tuguia.core.user.repositories

import edu.ort.tuguia.core.user.domain.User
import edu.ort.tuguia.core.user.domain.UserRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
@Transactional
@Profile("production|default")
class UserPostgresRepository : UserRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun saveUser(user: User) {
        em.persist(user)
    }

    override fun getUserByUsername(username: String): User? {
        return em.find(User::class.java, username)
    }

    override fun getAllUsers(): List<User> {
        val query = em.criteriaBuilder.createQuery(User::class.java)
        val from = query.from(User::class.java)
        val select = query.select(from)
            .orderBy(em.criteriaBuilder.asc(from.get<User>("username")))

        return em.createQuery(select).resultList
    }

    override fun deleteUser(user: User) {
        em.remove(user)
    }
}