package ru.itis.orisjavaproject.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.orisjavaproject.Entities.News;

import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {
    @Transactional
    @Modifying
    @Query("UPDATE News n SET n.title = :title, n.content = :content WHERE n.id = :id")
    void update(@Param("id") UUID id, @Param("title") String title, @Param("content") String content);

}