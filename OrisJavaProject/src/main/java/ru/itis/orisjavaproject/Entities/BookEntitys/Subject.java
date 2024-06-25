package ru.itis.orisjavaproject.Entities.BookEntitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.itis.orisjavaproject.Repositories.BookRepository;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "subjects")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "subjects")
    @JsonIgnore
    private Set<Book> books;

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public Subject(String subject){
        name = subject;
    }
}