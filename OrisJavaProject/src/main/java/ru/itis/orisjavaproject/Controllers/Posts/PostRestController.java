package ru.itis.orisjavaproject.Controllers.Posts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.orisjavaproject.Entities.Post;
import ru.itis.orisjavaproject.Services.PostsService.PostService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Posts API")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @Operation(summary = "Get all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all posts")
    })
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(summary = "Get post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@Parameter(description = "ID of the post to be obtained. Cannot be empty.",
            required = true) @PathVariable UUID id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post created successfully")
    })
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    @Operation(summary = "Update an existing post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@Parameter(description = "ID of the post to be updated. Cannot be empty.",
            required = true) @PathVariable UUID id, @RequestBody Post postDetails) {
        return ResponseEntity.ok(postService.updatePost(id, postDetails));
    }

    @Operation(summary = "Delete a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@Parameter(description = "ID of the post to be deleted. Cannot be empty.",
            required = true) @PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}