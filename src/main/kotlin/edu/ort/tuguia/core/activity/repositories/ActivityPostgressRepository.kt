package edu.ort.tuguia.core.activity.repositories

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.activity.domain.ActivityRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
@Transactional
@Profile("production")
class ActivityPostgressRepository : ActivityRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun createActivity(activity: Activity) {
        em.persist(activity)
    }

    override fun updateActivity(activity: Activity) {
        em.persist(activity)
    }

    override fun getActivityById(id: String): Activity? {
        return em.find(Activity::class.java, id)
    }

    override fun getAllActivities(): List<Activity> {
        val query = em.criteriaBuilder.createQuery(Activity::class.java)
        val from = query.from(Activity::class.java)
        val select = query.select(from).orderBy(em.criteriaBuilder.asc(from.get<Activity>("createdAt")))

        return em.createQuery(select).resultList
    }

    override fun getActivitiesByUsername(username: String): List<Activity> {
        val query = em.criteriaBuilder.createQuery(Activity::class.java)
        val from = query.from(Activity::class.java)
        val select = query.select(from).where(em.criteriaBuilder.equal(from.get<Activity>("guideUsername"), username))

        return em.createQuery(select).resultList
    }

    override fun getActivitiesByCategories(categoriesIds: List<String>): List<Activity> {
        val query = em.criteriaBuilder.createQuery(Activity::class.java)
        val from = query.from(Activity::class.java)
        val select = query.select(from).where(from.get<Activity>("categoryId").`in`(categoriesIds))

        return em.createQuery(select).resultList
    }

    override fun deleteActivity(activity: Activity) {
        em.remove(activity)
    }
}