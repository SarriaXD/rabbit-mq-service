package com.felix.rabbitmqservice.dto

import com.felix.rabbitmqservice.model.Post

data class PostDTO(
    val id: Long,
    val userId: Long?,
    val originalVideoUrl: String?,
    val finalVideoUrl: String?
)

fun PostDTO.toPost() = Post(
    id = id,
    userId = userId,
    originalVideoUrl = originalVideoUrl,
    finalVideoUrl = finalVideoUrl
)