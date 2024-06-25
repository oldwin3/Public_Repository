package ru.itis.orisjavaproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import ru.itis.orisjavaproject.Entities.User.State;

@Data
@AllArgsConstructor
public class UserDto {
    private String email;
    private String firstName;
    private String lastName;
    private State state;
}