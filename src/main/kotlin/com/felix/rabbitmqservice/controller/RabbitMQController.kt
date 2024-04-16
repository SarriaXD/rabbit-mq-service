package com.felix.rabbitmqservice.controller

import com.felix.rabbitmqservice.publisher.VideoTransformPublisher
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * You can ignore this
 * You don't need this, just test the rabbit mq
 */
@RestController
@RequestMapping("/mq")
class RabbitMQController(
    private val publisher: VideoTransformPublisher
) {

    @GetMapping("/video-transform/{postId}")
    // description for api doc, you can visit http://localhost:8080/swagger-ui.html
    @ApiResponse(description = "send video transform message")
    fun send(@PathVariable postId: Long) {
        publisher.sendVideoTransformMessage(postId)
    }
}