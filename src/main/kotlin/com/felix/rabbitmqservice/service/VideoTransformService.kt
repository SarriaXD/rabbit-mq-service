package com.felix.rabbitmqservice.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.UUID
import kotlin.io.path.Path

@Service
class VideoTransformService(
    private val s3Service: S3Service,
    @Value("\${custom.video.temp}")
    private val tempDir: String
) {
    // fetch the video from s3 and transform it
    fun transformVideo(originalVideo: String): String {
        val tempVideoName = "${UUID.randomUUID()}.${originalVideo.extension}"
        // check if the video is valid
        if (!originalVideo.isValidVideo) {
            throw IllegalArgumentException("Invalid video format")
        }
        val tempOutputDir = "$tempDir/${tempVideoName.nameWithoutExtension}"

        try {
            // download the original video from s3
            val originalVideoFile = s3Service.download(
                destinationPath = Path("$tempDir/$tempVideoName"),
                key = originalVideo
            )
            // convert the video to hls
            convertToHls(originalVideoFile.absolutePath, tempOutputDir)
            // upload the hls files to s3, the key is the original video name / filename
            val hlsFiles = File(tempOutputDir).listFiles()
            hlsFiles?.forEach {
                s3Service.upload(
                    file = it,
                    key = "${originalVideo.uploadFolder}/${it.name}"
                )
            }
            return originalVideo.uploadFolder
        } catch (e: Exception) {
            throw e
        } finally {
            // delete the temp files
            // 1.delete the original video
            File("$tempDir/$tempVideoName").delete()
            // 2.delete the hls files
            File(tempOutputDir).deleteRecursively()
        }
    }

    private val String.isValidVideo
        get() = nameWithoutExtension.isNotBlank() && (this.endsWith(".mp4") || this.endsWith(".mov") || this.endsWith(".avi"))

    // get the name of the file without extension
    private val String.nameWithoutExtension: String
        get() {
            val name = this.substringBeforeLast(".")
            return name.substringAfterLast("/")
        }

    private val String.extension
        get() = this.substringAfterLast(".")

    private val String.uploadFolder
        get() = this.substringBeforeLast(".")

    private val resolutions = arrayOf("240x360", "480x720", "1080x2048")
    private fun convertToHls(originalFilePath: String, outputDir: String) {
        File(outputDir).mkdirs()
        resolutions.forEach { resolution ->
            val outputPath = "$outputDir/${resolution}_output.m3u8"
            // use native command
            val command =
                "ffmpeg -i $originalFilePath -profile:v baseline -level 3.0 -s $resolution -start_number 0 -hls_time 10 -hls_list_size 0 -f hls $outputPath"
            Runtime.getRuntime().exec(command).apply {
                waitFor()
                BufferedReader(InputStreamReader(inputStream)).forEachLine { println(it) }
            }
        }

    }
}