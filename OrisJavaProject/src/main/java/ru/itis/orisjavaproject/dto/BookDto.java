package ru.itis.orisjavaproject.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itis.orisjavaproject.Entities.BookEntitys.Author;
import ru.itis.orisjavaproject.Entities.BookEntitys.Subject;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private Set<Subject> subjects;
    private Set<Author> authors;
}