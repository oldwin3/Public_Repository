package ru.itis.orisjavaproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.orisjavaproject.Entities.Image;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    Optional<Image> findByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM images ORDER BY RANDOM() LIMIT 1")
    Optional<Image> findRandomImage();
}