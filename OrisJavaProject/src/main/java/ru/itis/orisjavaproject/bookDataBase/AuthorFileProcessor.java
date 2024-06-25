package ru.itis.orisjavaproject.bookDataBase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.itis.orisjavaproject.Entities.BookEntitys.Author;
import ru.itis.orisjavaproject.Repositories.AuthorRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class AuthorFileProcessor implements CommandLineRunner {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorFileProcessor(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath = "C:/!Test/ol_dump_authors_2024-04-30.txt";
        processFile(filePath);
    }

    private void processFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String jsonString = line.substring(line.indexOf('{'));
                JsonNode rootNode = objectMapper.readTree(jsonString);
                String name = rootNode.path("name").asText();
                String key = rootNode.path("key").asText();

                Author author = Author.builder()
                        .name(name)
                        .key(key)
                        .build();

                authorRepository.save(author);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}