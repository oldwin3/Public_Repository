package ru.itis.orisjavaproject.Repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.orisjavaproject.Entities.EntityesForMoodTest.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT q FROM Question q JOIN FETCH q.options")
    List<Question> findAllWithOptions();
}