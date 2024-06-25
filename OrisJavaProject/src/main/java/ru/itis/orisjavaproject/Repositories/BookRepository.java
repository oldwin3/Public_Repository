package ru.itis.orisjavaproject.Repositories;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;

import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @EntityGraph(attributePaths = {"authors", "subjects"})
    @Override
    List<Book> findAll();

    @Query("SELECT DISTINCT b FROM Book b " +
            "LEFT JOIN FETCH b.authors a " +
            "LEFT JOIN FETCH b.subjects s " +
            "LEFT JOIN FETCH b.users u " +
            "WHERE u.id = :userId")
    List<Book> findAllBooksByUserid(@Param("userId") UUID userId);

    @EntityGraph(attributePaths = {"authors", "subjects"})
    Optional<Book> findByTitle(String title);

    @EntityGraph(attributePaths = {"authors", "subjects"})
    Optional<Book> findById(UUID bookid);

    @Modifying
    @Query(value = "DELETE FROM account_book WHERE account_id = :userId AND book_id = :bookId", nativeQuery = true)
    void deleteBookLinkById(@Param("userId") UUID userId, @Param("bookId") UUID bookId);

}