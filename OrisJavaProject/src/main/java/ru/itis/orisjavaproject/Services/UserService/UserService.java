package ru.itis.orisjavaproject.Services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.Entities.Group;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Repositories.BookRepository;
import ru.itis.orisjavaproject.Repositories.UserRepository;
import ru.itis.orisjavaproject.exception.NotFoundServiceException;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    @Transactional
    public void updateUserGroup(UUID userId, Group group) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundServiceException("User not found with id: " + userId));

        user.setGroup(group);
        userRepository.save(user);
    }

    public Optional<User> getUserProfilebyEmail(UUID userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }


    public void updateUserProfile(UUID userId, String email, String firstName, String lastName, MultipartFile profilePicture) {
        Optional<User> userOptional = userRepository.findById(userId);
        // œŒ◊≈ ¿“‹ » ”¡–¿“‹ »‘€ ≈—À» Õ¿ƒŒ
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            if (email != null && !email.isEmpty()) {
                existingUser.setEmail(email);
            }
            if (firstName != null && !firstName.isEmpty()) {
                existingUser.setFirstName(firstName);
            }
            if (lastName != null && !lastName.isEmpty()) {
                existingUser.setLastName(lastName);
            }
            if (profilePicture != null && !profilePicture.isEmpty()) {
                try {
                    existingUser.setProfilePicture(profilePicture.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            userRepository.save(existingUser);
        }
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public void removeUsersFromGroup(Group group) {
        for (User u : group.getUsers()) {
            updateUserGroup(u.getId(), null);
        }
    }
}
