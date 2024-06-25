package ru.itis.orisjavaproject.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.Image;
import ru.itis.orisjavaproject.Repositories.ImageRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Optional<Image> getRandomImage() {
        return imageRepository.findRandomImage();
    }
}