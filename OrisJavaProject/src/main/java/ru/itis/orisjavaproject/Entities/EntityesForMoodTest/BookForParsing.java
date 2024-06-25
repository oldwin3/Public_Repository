package ru.itis.orisjavaproject.Entities.EntityesForMoodTest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.itis.orisjavaproject.Entities.BookEntitys.Author;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookForParsing {
    private String key;
    private String title;
    private List<Author> authors;
    private String[] subject;
}