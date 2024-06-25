package ru.itis.orisjavaproject.Entities.BookEntitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "author")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String key;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Book> books;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}