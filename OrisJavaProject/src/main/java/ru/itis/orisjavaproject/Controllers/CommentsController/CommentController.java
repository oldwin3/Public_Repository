package ru.itis.orisjavaproject.Controllers.CommentsController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.orisjavaproject.Entities.Comment;
import ru.itis.orisjavaproject.Entities.Post;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.CommentsService.CommentService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public String getAllCommentsByPostId(@PathVariable(value = "postId") UUID postId, Model model) {
        log.info("Getting all comments for post with id: {}", postId);
        List<Comment> comments = commentService.getAllCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        Post post = new Post();
        post.setId(postId);
        model.addAttribute("post", post);
        return "comments";
    }
    @GetMapping("/new")
    public String showCreateCommentForm() {
        log.info("Showing create comment form");
        return "create_comment";
    }

    @PostMapping("/new")
    public String createComment(@PathVariable(value = "postId") UUID postId,
                                @RequestParam String content) {
        log.info("Creating new comment for post with id: {}", postId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(new Post(postId));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        comment.setUser(userDetails.getUser());
        commentService.createComment(postId, comment);
        return "redirect:/posts/{postId}";
    }
}