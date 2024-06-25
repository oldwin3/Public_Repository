package ru.itis.orisjavaproject.Services.CommentsService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.Comment;
import ru.itis.orisjavaproject.Entities.Post;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Mappers.CommentMapper;
import ru.itis.orisjavaproject.Repositories.CommentRepository;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.dto.CommentDto;
import ru.itis.orisjavaproject.exception.NotFoundServiceException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    public List<Comment> getAllCommentsByPostId(UUID postId) {
        return commentRepository.findByPostId(postId);
    }
    public Optional<Comment> getCommentById(UUID id) {
        return commentRepository.findById(id);
    }

    public Comment createComment(UUID postId, Comment comment) {
        comment.setPost(new Post(postId));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        comment.setUser(userDetails.getUser());
        return commentRepository.save(comment);
    }


}