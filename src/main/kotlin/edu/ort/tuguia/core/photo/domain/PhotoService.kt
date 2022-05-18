package edu.ort.tuguia.core.photo.domain

import com.google.auth.Credentials
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.*
import com.google.firebase.messaging.SendResponse
import edu.ort.tuguia.core.category.domain.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.*

@Service
class PhotoService(private val photoRepository: PhotoRepository) {

    fun upload(multipartFile: MultipartFile): Any {
        return try {
            var fileName = multipartFile.originalFilename // to get original file name
            fileName = UUID.randomUUID()
                .toString() + getExtension(fileName!!) // to generated random string values for file name.
            val file = convertToFile(multipartFile, fileName) // to convert multipartFile to File
            var TEMP_URL : String = uploadFile(file!!, fileName) // to get uploaded file link
            file.delete() // to delete the copy of uploaded file stored in the project folder
            //SendResponse("Successfully Uploaded !",TEMP_URL) // Your customized response
            var photo = Photo()
            photo.createdAt = LocalDateTime.now()
            photo.filename = fileName
            photoRepository.savePhoto(photo)
            return ("Successfully Uploaded !$TEMP_URL");
        } catch (e: Exception) {
            e.printStackTrace()
            return("500 $e Unsuccessfully Uploaded!")
        }
    }

    @Throws(IOException::class)
    fun download(fileName: String): Any {
        val destFileName = UUID.randomUUID().toString() + getExtension(fileName!!) // to set random strinh for destination file name
        val destFilePath = "Storage/" + destFileName;  // to set destination file path

        ////////////////////////////////   Download  ////////////////////////////////////////////////////////////////////////
        val credentials: Credentials = GoogleCredentials.fromStream(FileInputStream("src/main/resources/tuguia-ort-firebase.json"))
        val storage = StorageOptions.newBuilder().setCredentials(credentials).build().service
        val blob: Blob = storage[BlobId.of("tuguia-ort.appspot.com", fileName)]

        blob.downloadTo(Paths.get(destFilePath))
        return ("200 Successfully Downloaded!")
    }

    private fun uploadFile(file: File, fileName: String): String {
        val blobId = BlobId.of("tuguia-ort.appspot.com", fileName)
        val blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build()
        val credentials: Credentials = GoogleCredentials.fromStream(FileInputStream("src/main/resources/tuguia-ort-firebase.json"))
        val storage: Storage = StorageOptions.newBuilder().setCredentials(credentials).build().service
        storage.create(blobInfo, Files.readAllBytes(file.toPath()))
        return java.lang.String.format("https://console.firebase.google.com/project/tuguia-ort/storage/tuguia-ort.appspot.com/files", URLEncoder.encode(fileName, StandardCharsets.UTF_8))
    }

    @Throws(IOException::class)
    private fun convertToFile(multipartFile: MultipartFile, fileName: String): File {
        val tempFile = File(fileName)
        FileOutputStream(tempFile).use { fos ->
            fos.write(multipartFile.bytes)
            fos.close()
        }
        return tempFile
    }

    private fun getExtension(fileName: String): String {
        return fileName.substring(fileName.lastIndexOf("."))
    }
}