package edu.ort.tuguia.core.photo.repositories

import edu.ort.tuguia.core.category.domain.Category
import edu.ort.tuguia.core.photo.domain.Photo
import edu.ort.tuguia.core.photo.domain.PhotoRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
@Transactional
@Profile("production|default")
class PhotoPostgresRepository: PhotoRepository {
    @PersistenceContext
    private lateinit var em:EntityManager

    override fun savePhoto(photo: Photo) {
        em.persist(photo)
    }

    override fun getPhotoById(id: String): Photo? {
        return em.find(Photo::class.java, id)
    }

    override fun getPhotoByName(name: String): Photo? {
        val query = em.criteriaBuilder.createQuery(Photo::class.java)
        val from = query.from(Photo::class.java)
        val select = query.select(from).where(em.criteriaBuilder.equal(em.criteriaBuilder.lower(from.get("name")), name.lowercase()))

        return try {
            em.createQuery(select).singleResult
        } catch (ex: NoResultException) {
            null
        }
    }

    override fun getAllPhotos(): List<Photo> {
        val query = em.criteriaBuilder.createQuery(Photo::class.java)
        val from = query.from(Photo::class.java)
        val select = query.select(from).orderBy(em.criteriaBuilder.asc(from.get<Photo>("createdAt")))

        return em.createQuery(select).resultList
    }

    override fun deletePhotos(photo: Photo) {
        em.remove(photo)
    }

}