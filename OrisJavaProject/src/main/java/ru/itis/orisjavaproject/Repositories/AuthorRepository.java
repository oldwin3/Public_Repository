package ru.itis.orisjavaproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.orisjavaproject.Entities.BookEntitys.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
    @Query("SELECT a FROM Author a WHERE a.name = :name")
    Optional<Author> findByName(String name);

    @Query("SELECT a FROM Author a WHERE a.name = :name")
    List<Author> findByNameList(String name);

    @Query("SELECT a FROM Author a JOIN a.books b WHERE a.name = :name AND b.id IS NOT NULL")
    Optional<Author> findFirstAuthorWithBooksByName(String name);
}