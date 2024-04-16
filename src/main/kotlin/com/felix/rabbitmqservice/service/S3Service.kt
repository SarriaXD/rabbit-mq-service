package com.felix.rabbitmqservice.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File
import java.nio.file.Path

@Service
class S3Service(
    private val s3Client: S3Client,
    // read value from application.properties
    @Value("\${custom.s3.bucket}")
    private val bucket: String
) {
    fun upload(
        file: File,
        key: String,
    ) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build()
        val requestBody = RequestBody.fromFile(file)
        s3Client.putObject(
            putObjectRequest,
            requestBody
        )
    }

    fun download(
        destinationPath: Path,
        key: String,
    ): File {
        s3Client.getObject(
            {
                it.bucket(bucket)
                    .key(key)
                    .build()
            },
            destinationPath
        )
        return destinationPath.toFile()
    }

}