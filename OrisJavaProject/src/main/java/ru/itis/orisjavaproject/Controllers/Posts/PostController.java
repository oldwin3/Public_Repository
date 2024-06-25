package ru.itis.orisjavaproject.Controllers.Posts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.orisjavaproject.Entities.Post;
import ru.itis.orisjavaproject.Mappers.CommentMapper;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.CommentsService.CommentService;
import ru.itis.orisjavaproject.Services.ImageService;
import ru.itis.orisjavaproject.Services.PostsService.PostService;
import ru.itis.orisjavaproject.dto.CommentDto;
import ru.itis.orisjavaproject.dto.PostDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentMapper commentMapper;
    private final ImageService imageService;


    @GetMapping("/{id}")
    public String getPostById(@PathVariable UUID id, Model model) {
        log.info("Getting post by id: {}", id);
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        Post post = postService.getPostById(id).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.getUser().getId().equals(post.getUser().getId())) {
            model.addAttribute("flag", true);
        } else {
            model.addAttribute("flag", false);
        }
        model.addAttribute("post", post);
        List<CommentDto> commentDtos = post.getComments().stream()
                .map(commentMapper::commentToCommentDtoWithUserName)
                .collect(Collectors.toList());
        model.addAttribute("comments", commentDtos);
        return "Posts/post";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        log.info("Showing create post form");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return "Posts/post_form";
    }

    @PostMapping("/new")
    public String createPost(@RequestParam String title, @RequestParam String content) {
        log.info("Creating new post with title: {}", title);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Post post =  Post.builder().title(title).content(content).user(userDetails.getUser()).build();
        postService.createPost(post);
        return "redirect:/users/" + userDetails.getUser().getId();
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(Model model) {
        log.info("Showing update post form");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return "Posts/post_form";
    }

    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable UUID id, @RequestParam String title, @RequestParam String content) {
        log.info("Updating post with id: {}", id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Post post =  Post.builder().title(title).content(content).user(userDetails.getUser()).build();
        Post post2 = postService.getPostById(id).get();

        if (!post2.getUser().getId().equals(userDetails.getUser().getId())) {
            log.error("User {} is not authorized to edit post {}", userDetails.getUser().getId(), id);
            throw new AccessDeniedException("You are not authorized to edit this post");
        }
        postService.updatePost(id, post);
        return "redirect:/users/" + userDetails.getUser().getId();
    }

    @PostMapping("/{id}")
    public String deletePost(@PathVariable UUID id) {
        log.info("Deleting post with id: {}", id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Post post = postService.getPostById(id).get();
        if (!post.getUser().getId().equals(userDetails.getUser().getId())) {
            log.error("User {} is not authorized to delete post {}", userDetails.getUser().getId(), id);
            throw new AccessDeniedException("You are not authorized to edit this post");
        }
        postService.deletePost(id);
        return "redirect:/users/" + userDetails.getUser().getId();
    }
}