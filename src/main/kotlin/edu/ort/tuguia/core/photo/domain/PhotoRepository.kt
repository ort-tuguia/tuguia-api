package edu.ort.tuguia.core.photo.domain



interface PhotoRepository {
    fun savePhoto(photo: Photo)
    fun getPhotoById(id: String): Photo?
    fun getPhotoByName(name: String): Photo?
    fun getAllPhotos(): List<Photo>
    fun deletePhotos(photo: Photo)
}