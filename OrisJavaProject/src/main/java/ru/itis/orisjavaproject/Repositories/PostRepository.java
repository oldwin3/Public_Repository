package ru.itis.orisjavaproject.Repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.orisjavaproject.Entities.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @EntityGraph(attributePaths = {"comments"})
    Optional<Post> findWithCommentsById(UUID id);
    List<Post> findByUserId(UUID userId);
}