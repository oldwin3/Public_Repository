package ru.itis.orisjavaproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.orisjavaproject.Entities.EntityesForMoodTest.MoodScore;

public interface 
MoodScoreRepository extends JpaRepository<MoodScore, Long> {
}