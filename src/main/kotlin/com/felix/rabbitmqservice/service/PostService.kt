package com.felix.rabbitmqservice.service

import com.felix.rabbitmqservice.exception.PostServiceException
import com.felix.rabbitmqservice.model.Post
import com.felix.rabbitmqservice.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {

    // try catch is not necessary here if you don't need custom exception handling and error message
    fun getPostById(id: Long): Post {
        try {
            return postRepository.findById(id).orElseThrow { PostServiceException.PostNotFoundServiceException() }
        } catch (e: PostServiceException.PostNotFoundServiceException) {
            // PostNotFoundServiceException will be caught here, so need to throw it again
            throw e
        } catch (e: Exception) {
            throw PostServiceException.PostGetServiceException()
        }
    }

    // try catch is not necessary here if you don't need custom exception handling and error message
    fun addPost(post: Post): Post {
        try {
            if (postRepository.existsById(post.id)) {
                throw PostServiceException.PostAlreadyExistsServiceException()
            }
            return postRepository.save(post)
        } catch (e: PostServiceException.PostAlreadyExistsServiceException) {
            // PostAlreadyExistsServiceException will be caught here, so need to throw it again
            throw e
        } catch (e: Exception) {
            throw PostServiceException.PostNotCreatedServiceException()
        }
    }

    // try catch is not necessary here if you don't need custom exception handling and error message
    fun deletePostById(id: Long) {
        try {
            if (!postRepository.existsById(id)) {
                throw PostServiceException.PostNotFoundServiceException()
            }
            postRepository.deleteById(id)
        } catch (e: PostServiceException.PostNotFoundServiceException) {
            // PostNotFoundServiceException will be caught here, so need to throw it again
            throw e
        } catch (e: Exception) {
            throw PostServiceException.PostNotDeletedServiceException()
        }
    }

    // try catch is not necessary here if you don't need custom exception handling and error message
    fun updatePost(updatePost: Post): Post {
        try {
            if (!postRepository.existsById(updatePost.id)) {
                throw PostServiceException.PostNotFoundServiceException()
            }
            return postRepository.save(updatePost)
        } catch (e: PostServiceException.PostNotFoundServiceException) {
            // PostNotFoundServiceException will be caught here, so need to throw it again
            throw e
        } catch (e: Exception) {
            throw PostServiceException.PostNotUpdatedServiceException()
        }
    }
}