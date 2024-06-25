package ru.itis.orisjavaproject.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.orisjavaproject.Entities.BookEntitys.Author;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.Entities.BookEntitys.Subject;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findOneByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.state = 'BANNED' WHERE u.role != 'ADMIN'")
    void banAllUsersExceptAdmins();

    static Specification<User> hasRole(User.Role role) {
        return (user, cq, cb) -> cb.equal(user.get("role"), role);
    }

    @EntityGraph(attributePaths = {"books"})
    @Query("SELECT DISTINCT a FROM User u JOIN u.books b JOIN b.authors a WHERE u.id = :userId")
    Set<Author> findAuthorsByUserId(@Param("userId") UUID userId);

    @EntityGraph(attributePaths = {"books"})
    @Query("SELECT s FROM Subject s " +
            "JOIN s.books b " +
            "JOIN b.users    u " +
            "WHERE u = :user " +
            "GROUP BY s.id " +
            "ORDER BY COUNT(s) DESC")
    List<Subject> findMostFrequentSubjectsByUser(@Param("user") User user, Pageable pageable);

    @Query("SELECT a, COUNT(a) as authorCount FROM User u " +
            "JOIN u.books b " +
            "JOIN b.authors a " +
            "WHERE u = :user " +
            "GROUP BY a " +
            "ORDER BY authorCount DESC")
    List<Author> findMostFrequentAuthorsByUser(@Param("user") User user, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO account_book (account_id, book_id) VALUES (:userId, :bookId)", nativeQuery = true)
    void addBookToUser(@Param("userId") UUID userId, @Param("bookId") UUID bookId);

    @Query("SELECT b FROM Book b JOIN b.users u WHERE u.id = :userId")
    List<Book> findAllBooksByUserId(@Param("userId") UUID userId);
}
