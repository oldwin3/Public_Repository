package ru.itis.orisjavaproject.Services.PostsService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.Post;
import ru.itis.orisjavaproject.Repositories.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public List<Post> getPostsByUserId(UUID userId) {
        return postRepository.findByUserId(userId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(UUID id) {
        return postRepository.findWithCommentsById(id);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(UUID id, Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());

        return postRepository.save(post);
    }

    public void deletePost(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        postRepository.delete(post);
    }
}