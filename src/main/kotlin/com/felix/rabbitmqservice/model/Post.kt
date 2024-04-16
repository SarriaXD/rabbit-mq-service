package com.felix.rabbitmqservice.model

import jakarta.persistence.*

@Entity
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "user_id")
    val userId: Long?,
    @Column(name = "original_video_url")
    val originalVideoUrl: String?,
    @Column(name = "final_video_url")
    val finalVideoUrl: String?,
)
