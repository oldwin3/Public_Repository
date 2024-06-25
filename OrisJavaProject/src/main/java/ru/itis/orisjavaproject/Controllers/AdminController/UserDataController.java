package ru.itis.orisjavaproject.Controllers.AdminController;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Repositories.UserRepository;
import ru.itis.orisjavaproject.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserDataController {

    private final UserRepository userRepository;

    @GetMapping("/userdata")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> getUserData() {
        return userRepository.findAll()
                .stream()
                .map(User::toDto)
                .toList();
    }
}
