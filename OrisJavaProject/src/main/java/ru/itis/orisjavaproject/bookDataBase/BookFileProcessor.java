package ru.itis.orisjavaproject.BookDataBase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.itis.orisjavaproject.Entities.BookEntitys.Author;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.Entities.BookEntitys.Subject;
import ru.itis.orisjavaproject.Repositories.AuthorRepository;
import ru.itis.orisjavaproject.Repositories.BookRepository;
import ru.itis.orisjavaproject.Repositories.SubjectRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class BookFileProcessor implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public BookFileProcessor(BookRepository bookRepository, AuthorRepository authorRepository, SubjectRepository subjectRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath = "C:/!Test/ol_dump_works_2024-04-30.txt";
        processFile(filePath);
    }

    private void processFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (int i = 1; i < 11478; i++) {
                reader.readLine();
            }
            String line;
            while ((line = reader.readLine()) != null) {
                String jsonString = line.substring(line.indexOf('{'));
                JsonNode rootNode = objectMapper.readTree(jsonString);
                String title = rootNode.path("title").asText();
                JsonNode authorsNode = rootNode.path("authors");
                JsonNode subjectsNode = rootNode.path("subjects");

                Book book = Book.builder()
                        .title(title)
                        .build();

                book = bookRepository.save(book);


                if (authorsNode.isArray()) {
                    for (JsonNode authorNode : authorsNode) {
                        if (!authorNode.isMissingNode()) {
                            String authorKey = authorNode.path("author").path("key").asText();
                            Optional<Author> authorOptional = authorRepository.findByKey(authorKey);
                            if (authorOptional.isPresent()) {
                                Author author = authorOptional.get();
                                bookRepository.addAuthorToBook(book.getId(), author.getId());
                            }
                        }
                    }
                }

                Set<Subject> subjects = new HashSet<>();
                if (subjectsNode.isArray()) {
                    for (JsonNode subjectNameNode : subjectsNode) {
                        String subjectName = subjectNameNode.asText();
                        Optional<Subject> subjectOptional = subjectRepository.findByName(subjectName);
                        if (subjectOptional.isPresent()) {
                            Subject subject = subjectOptional.get();
                            bookRepository.addSubjectToBook(book.getId(), subject.getId());
                        }
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}