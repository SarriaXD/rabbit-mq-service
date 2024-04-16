package com.felix.rabbitmqservice.publisher

import com.felix.rabbitmqservice.config.RabbitMQConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class VideoTransformPublisher(
    private val rabbitTemplate: RabbitTemplate,
) {
    fun sendVideoTransformMessage(postId: Long) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.VIDEO_TRANSFORM_EXCHANGE,
            RabbitMQConfig.VIDEO_TRANSFORM_ROUTER_KEY,
            postId
        )
    }
}