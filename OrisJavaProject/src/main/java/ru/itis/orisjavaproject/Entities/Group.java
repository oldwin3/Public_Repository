package ru.itis.orisjavaproject.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @Column(name = "user_id")
    private UUID creator;

    @Column(name = "grouppicture")
    private byte[] groupPicture;

}