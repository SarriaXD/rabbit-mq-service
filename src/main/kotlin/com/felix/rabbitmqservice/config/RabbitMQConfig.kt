package com.felix.rabbitmqservice.config

import org.springframework.amqp.core.*
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {
    @Bean
    fun videoTransformQueue(): Queue {
        return Queue(VIDEO_TRANSFORM_QUEUE, true)
    }

    @Bean
    fun videoTransformExchange(): TopicExchange {
        return ExchangeBuilder
            .topicExchange(VIDEO_TRANSFORM_EXCHANGE)
            .durable(true)
            .build()
    }

    @Bean
    fun bindingVideoTransformQueue(videoTransformQueue: Queue, videoTransformExchange: TopicExchange): Binding {
        return BindingBuilder
            .bind(videoTransformQueue)
            .to(videoTransformExchange)
            .with(VIDEO_TRANSFORM_ROUTER_KEY)
    }

    // support json message from rabbitmq message
    // when accept message from rabbitmq, we can use class to accept the message
    @Bean
    fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    companion object {
        const val VIDEO_TRANSFORM_QUEUE = "video-transform-queue"
        const val VIDEO_TRANSFORM_EXCHANGE = "video-transform-exchange"
        const val VIDEO_TRANSFORM_ROUTER_KEY = "video-transform-key"
    }
}