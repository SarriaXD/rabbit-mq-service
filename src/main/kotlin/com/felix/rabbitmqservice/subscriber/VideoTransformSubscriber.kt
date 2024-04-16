package com.felix.rabbitmqservice.subscriber

import com.felix.rabbitmqservice.config.RabbitMQConfig
import com.felix.rabbitmqservice.exception.PostServiceException
import com.felix.rabbitmqservice.service.PostService
import com.felix.rabbitmqservice.service.VideoTransformService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service


@Service
class VideoTransformSubscriber(
    private val videoTransformService: VideoTransformService,
    private val postService: PostService
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    @RabbitListener(queues = [RabbitMQConfig.VIDEO_TRANSFORM_QUEUE])
    fun handleMessage(postId: Long) {
        try {
            logger.info("Received message for post id: $postId")
            val post = postService.getPostById(postId)
            val transformedUrl = videoTransformService.transformVideo(post.originalVideoUrl!!)
            val transformedPost = post.copy(finalVideoUrl = transformedUrl)
            postService.updatePost(transformedPost)
        } catch (e: PostServiceException) {
            logger.error(e.exceptionMessage.toString())
        } catch (e: Exception) {
           logger.error(e.message)
        }
    }

}