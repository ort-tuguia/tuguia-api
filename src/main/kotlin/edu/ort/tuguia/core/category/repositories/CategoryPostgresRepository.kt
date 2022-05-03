package edu.ort.tuguia.core.category.repositories

import edu.ort.tuguia.core.category.domain.Category
import edu.ort.tuguia.core.category.domain.CategoryRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
@Transactional
@Profile("production|default")
class CategoryPostgresRepository : CategoryRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun saveCategory(category: Category) {
        em.persist(category)
    }

    override fun getCategoryById(id: String): Category? {
        return em.find(Category::class.java, id)
    }

    override fun getCategoryByName(name: String): Category? {
        val query = em.criteriaBuilder.createQuery(Category::class.java)
        val from = query.from(Category::class.java)
        val select = query.select(from).where(em.criteriaBuilder.equal(em.criteriaBuilder.lower(from.get("name")), name.lowercase()))

        return try {
            em.createQuery(select).singleResult
        } catch (ex: NoResultException) {
            null
        }
    }

    override fun getAllCategories(): List<Category> {
        val query = em.criteriaBuilder.createQuery(Category::class.java)
        val from = query.from(Category::class.java)
        val select = query.select(from).orderBy(em.criteriaBuilder.asc(from.get<Category>("createdAt")))

        return em.createQuery(select).resultList
    }

    override fun deleteCategory(category: Category) {
        em.remove(category)
    }
}