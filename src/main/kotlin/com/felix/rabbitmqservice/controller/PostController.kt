package com.felix.rabbitmqservice.controller

import com.felix.rabbitmqservice.dto.PostDTO
import com.felix.rabbitmqservice.dto.toPost
import com.felix.rabbitmqservice.service.PostService
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.*

/**
 *
 * For demo purpose, we only have get, post, delete, put methods
 */
@RestController
@RequestMapping("/post")
// Debug the api by visiting http://localhost:8080/swagger-ui.html
class PostController(
    private val postService: PostService
) {

    @GetMapping("/{id}")
    // description for api doc, you can visit http://localhost:8080/swagger-ui.html
    @ApiResponse(description = "get post by id")
    fun getPostById(@PathVariable id: Long) = postService.getPostById(id)

    @PostMapping
    @ApiResponse(description = "add post")
    fun addPost(@RequestBody postDTO: PostDTO) = postService.addPost(postDTO.toPost())

    @DeleteMapping("/{id}")
    @ApiResponse(description = "delete post by id")
    fun deletePostById(@PathVariable id: Long) = postService.deletePostById(id)

    @PutMapping
    @ApiResponse(description = "update post")
    fun updatePost(@RequestBody updatePostDTO: PostDTO) = postService.updatePost(updatePostDTO.toPost())

}