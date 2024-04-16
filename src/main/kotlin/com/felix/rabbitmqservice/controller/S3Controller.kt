package com.felix.rabbitmqservice.controller

import com.felix.rabbitmqservice.service.S3Service
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.web.multipart.MultipartFile
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.util.UUID

/**
 * You can ignore this
 * You don't need this, just test the s3
 */
@RestController
@RequestMapping("/s3")
class S3Controller(
    private val s3Service: S3Service,
    // read the value from application.properties
    @Value("\${custom.video.temp}")
    private val tempDir: String
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(@RequestParam("file") uploadFile: MultipartFile) {
        val tempFile = uploadFile.toTempFile()
        s3Service.upload(
            file = tempFile,
            key = tempFile.name
        )
    }

    private fun MultipartFile.toTempFile(): File {
        val tempFile = File("$tempDir/$originalFilename")
        logger.info(tempFile.toPath().toString())
        transferTo(tempFile.toPath())
        return tempFile
    }

    @GetMapping("/download/{filename}", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun download(@PathVariable filename: String): ResponseEntity<Resource> {
        val tempFile = File("$tempDir/${UUID.randomUUID()}")
        logger.info("temp file path: ${tempFile.absolutePath}")
        s3Service.download(
            destinationPath = tempFile.toPath(),
            key = filename
        )
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${filename}\"")
            add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
        }
        return ResponseEntity
            .ok()
            .headers(headers)
            .body(InputStreamResource(tempFile.inputStream()))
    }
}