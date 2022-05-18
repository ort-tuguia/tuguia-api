package edu.ort.tuguia.core.photo.domain


import edu.ort.tuguia.core.category.domain.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException


@Tag(name = "Photos")
@RestController
@RequestMapping("/api/photos")
class PhotoController(private val photoService: PhotoService) {

    var logger: Logger = LoggerFactory.getLogger(PhotoController::class.java)
    @Operation(summary = "Upload Photo")
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    fun upload(@RequestParam("file") multipartFile: MultipartFile): Any {
        logger.info("HIT -/upload | File Name : {}", multipartFile.originalFilename)
        return photoService.upload(multipartFile)
    }
    @Operation(summary = "Download Photo")
    @PostMapping("/{fileName}")
    @ResponseStatus(HttpStatus.OK)
    @Throws(IOException::class)
    fun download(@PathVariable fileName: String): Any {
        logger.info("HIT -/download | File Name : {}", fileName)
        return photoService.download(fileName)
    }
}