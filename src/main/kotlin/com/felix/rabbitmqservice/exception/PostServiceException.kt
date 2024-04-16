package com.felix.rabbitmqservice.exception

// maybe you want custom message with custom code instead of http status code
// it will be sent to the client as a response body
// you can move the ExceptionMessage to global package as base exception message if you want
data class ExceptionMessage(val message: String, val code: Int)

sealed class PostServiceException private constructor(val exceptionMessage: ExceptionMessage) :
    Exception(exceptionMessage.message) {

    constructor(message: String, code: Int) : this(ExceptionMessage(message, code))

    class PostNotFoundServiceException : PostServiceException("Post not found", 1)
    class PostAlreadyExistsServiceException : PostServiceException("Post already exists", 2)
    class PostGetServiceException : PostServiceException("Error in getting post", 3)
    class PostNotCreatedServiceException : PostServiceException("Error in creating post", 4)
    class PostNotUpdatedServiceException : PostServiceException("Error in updating post", 5)
    class PostNotDeletedServiceException : PostServiceException("Error in deleting post", 6)
}