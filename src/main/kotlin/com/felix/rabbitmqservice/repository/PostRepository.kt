package com.felix.rabbitmqservice.repository

import com.felix.rabbitmqservice.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>