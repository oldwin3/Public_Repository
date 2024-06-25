package ru.itis.orisjavaproject.bookDataBase;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainSubjectCommandLineRunner implements CommandLineRunner {

    private final MainSubjectService mainSubjectService;

    @Override
    public void run(String... args) throws Exception {
        mainSubjectService.updateMoodsForExistingGenres();
    }
}