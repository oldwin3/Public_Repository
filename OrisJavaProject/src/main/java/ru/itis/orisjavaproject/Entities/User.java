package ru.itis.orisjavaproject.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.dto.UserDto;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String email;

    @Column(length = 300)
    private String passwordHash;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private State state;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_book",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> posts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupid")
    private Group group;

    private byte[] profilePicture;
    public enum Role{
        USER,ADMIN
    }

    public enum State{
        ACTIVE,BANNED
    }

    public Boolean isActive(){
        return this.state == State.ACTIVE;
    }

    public Boolean isBanned(){
        return this.state == State.BANNED;
    }

    public Boolean isAdmin(){
        return this.role == Role.ADMIN;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto toDto() {
        return new UserDto(
                this.email,
                this.firstName,
                this.lastName,
                this.state
        );
    }
}
