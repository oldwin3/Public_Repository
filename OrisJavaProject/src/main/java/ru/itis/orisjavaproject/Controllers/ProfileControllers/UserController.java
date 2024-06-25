package ru.itis.orisjavaproject.Controllers.ProfileControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.ImageService;
import ru.itis.orisjavaproject.Services.UserService.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ImageService imageService;



    @GetMapping
    public String getAllUsers(Model model) {
        log.info("Getting all users");
        List<User> users = userService.getAllUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable UUID userId, Model model) {
        log.info("Getting user profile for userId: {}", userId);

        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        Optional<User> userOptional = userService.getUserProfilebyEmail(userId);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if (userOptional.get().getProfilePicture() != null) {
                String profilePictureBase64 = Base64.encodeBase64String(userOptional.get().getProfilePicture());
                model.addAttribute("profilePictureBase64", profilePictureBase64);
            }
            if(userDetails.getUser().getId().equals(userId)){
                model.addAttribute("flag", true);
            }
            else {
                model.addAttribute("flag", false);
            }
            return "userProfile";
        } else {
            log.warn("User not found for userId: {}", userId);
            return "erorr";
        }
    }

    @GetMapping("/{userId}/edit")
    public String editUserProfile(@PathVariable UUID userId, Model model) {
        log.info("Editing user profile for userId: {}", userId);
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        Optional<User> userOptional = userService.getUserProfilebyEmail(userId);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "editUserProfile";
        } else {
            log.warn("User not found for userId: {}", userId);
            return "erorr";
        }
    }

    @PostMapping("/{userId}/edit")
    public String updateUserProfile(@PathVariable UUID userId,
                                    @RequestParam(value = "email", required = false) String email,
                                    @RequestParam(value = "firstName", required = false) String firstName,
                                    @RequestParam(value = "lastName", required = false) String lastName,
                                    @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture) {
        log.info("Updating user profile for userId: {}", userId);
        userService.updateUserProfile(userId, email, firstName, lastName, profilePicture);
        return "redirect:/users/{userId}";
    }

}