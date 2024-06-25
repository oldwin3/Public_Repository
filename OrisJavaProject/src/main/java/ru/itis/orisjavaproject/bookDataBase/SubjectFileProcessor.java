package ru.itis.orisjavaproject.bookDataBase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.itis.orisjavaproject.Services.SubjectService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

@Component
public class SubjectFileProcessor implements CommandLineRunner {

    private final SubjectService subjectService;

    @Autowired
    public SubjectFileProcessor(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void run(String... args) throws Exception {
            String filePath = "C:/!Test/ol_dump_works_2024-04-30.txt";
            processFile(filePath);

    }

    private void processFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String jsonString = line.substring(line.indexOf('{'));
                JsonNode rootNode = objectMapper.readTree(jsonString);
                JsonNode subjectsNode = rootNode.path("subjects");
                if (subjectsNode.isArray()) {
                    Iterator<JsonNode> elements = subjectsNode.elements();
                    while (elements.hasNext()) {
                        String subject = elements.next().asText();
                        subjectService.saveSubject(subject);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}