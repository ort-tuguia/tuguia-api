package edu.ort.tuguia.core.activity.repositories

import edu.ort.tuguia.core.activity.domain.Activity
import edu.ort.tuguia.core.activity.domain.ActivityRepository
import edu.ort.tuguia.core.user.domain.User
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.Join
import javax.transaction.Transactional

@Repository
@Transactional
@Profile("production|default")
class ActivityPostgresRepository : ActivityRepository {
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
        val select = query.select(from)
            .where(em.criteriaBuilder.equal(from.get<Activity>("isDeleted"), false))
            .orderBy(em.criteriaBuilder.desc(from.get<Activity>("createdAt")))

        return em.createQuery(select).resultList
    }

    override fun getActivitiesByUsername(username: String): List<Activity> {
        val query = em.criteriaBuilder.createQuery(Activity::class.java)
        val from = query.from(Activity::class.java)
        val guide: Join<Activity, User> = from.join("guide")
        val select = query.select(from)
            .where(em.criteriaBuilder.and(
                em.criteriaBuilder.equal(from.get<Activity>("isDeleted"), false),
                em.criteriaBuilder.equal(guide.get<User>("username"), username)))
            .orderBy(em.criteriaBuilder.desc(from.get<Activity>("createdAt")))

        return em.createQuery(select).resultList
    }

    override fun getActivitiesByCategories(categoriesIds: List<String>): List<Activity> {
        val query = em.criteriaBuilder.createQuery(Activity::class.java)
        val from = query.from(Activity::class.java)
        val select = query.select(from)
            .where(em.criteriaBuilder.and(
                em.criteriaBuilder.equal(from.get<Activity>("isDeleted"), false),
                from.get<Activity>("categoryId").`in`(categoriesIds)))
            .orderBy(em.criteriaBuilder.desc(from.get<Activity>("createdAt")))

        return em.createQuery(select).resultList
    }

    override fun deleteActivity(activity: Activity) {
        em.remove(activity)
    }
}