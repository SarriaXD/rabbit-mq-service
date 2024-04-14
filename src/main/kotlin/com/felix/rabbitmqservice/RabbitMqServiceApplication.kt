package com.felix.rabbitmqservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RabbitMqServiceApplication

fun main(args: Array<String>) {
	runApplication<RabbitMqServiceApplication>(*args)
}
