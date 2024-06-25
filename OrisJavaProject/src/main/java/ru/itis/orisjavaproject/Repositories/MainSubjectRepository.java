package ru.itis.orisjavaproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.orisjavaproject.Entities.BookEntitys.MainSubject;
import ru.itis.orisjavaproject.Entities.BookEntitys.Subject;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MainSubjectRepository extends JpaRepository<MainSubject, Long> {
    List<MainSubject> findByNameIn(List<String> names);

    Optional<MainSubject> findByName(String name);

    @Query("SELECT ms.name FROM MainSubject ms WHERE ms.mood = :mood")
    List<String> findNamesByMood(@Param("mood") String mood);

    List<MainSubject> findAll();


}