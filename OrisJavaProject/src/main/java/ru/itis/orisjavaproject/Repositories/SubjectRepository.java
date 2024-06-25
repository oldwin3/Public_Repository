package ru.itis.orisjavaproject.Repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.orisjavaproject.Entities.BookEntitys.Subject;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    Optional<Subject> findByName(String name);
    @Query(value = "SELECT s.id, " +
            "s.name FROM subjects s JOIN book_subject bs ON s.id = bs.subject_id WHERE bs.book_id = :bookId",
            nativeQuery = true)
    Set<Subject> findSubjectsByBookId(@Param("bookId") UUID bookId);
    List<Subject> findByNameIn(List<String> names);
}