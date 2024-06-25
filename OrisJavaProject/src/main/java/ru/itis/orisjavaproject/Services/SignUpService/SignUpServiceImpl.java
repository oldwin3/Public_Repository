package ru.itis.orisjavaproject.Services.SignUpService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Entities.form.SignUpForm;
import ru.itis.orisjavaproject.Repositories.UserRepository;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UserRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpForm form) {
        User user = User.builder()
                .passwordHash(passwordEncoder.encode(form.getPassword()))
                .email(form.getEmail())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .group(null)
                .profilePicture(null)
                .build();

        usersRepository.save(user);
    }
}