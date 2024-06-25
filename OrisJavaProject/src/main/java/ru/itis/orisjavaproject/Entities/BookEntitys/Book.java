package ru.itis.orisjavaproject.Entities.BookEntitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.User;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_subject",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns ={@JoinColumn(name = "subject_id")})
    private Set<Subject> subjects;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subjects=" + subjects +
                ", authors=" + authors +
                '}';
    }
}