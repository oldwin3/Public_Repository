package ru.itis.orisjavaproject.Controllers.AdminController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.orisjavaproject.Entities.User;
import ru.itis.orisjavaproject.Repositories.UserRepository;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.AdminService.AdminService;
import ru.itis.orisjavaproject.Services.ImageService;

import java.util.List;

import static ru.itis.orisjavaproject.Repositories.UserRepository.hasRole;

@Controller
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminLinksController {
    private final UserRepository userRepository;

    private final AdminService service;
    private final ImageService imageService;

    @GetMapping("/get-all-user")
    @ResponseBody
    public List<User> getAllUser(Model model) {
        log.info("Getting all users");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return service.getAllUser();
    }

    @PostMapping("/ban-all-user")
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public void banAllUser() {
        log.info("Banning all users except admin");
        service.banAllUsersExceptAdmin();
    }

    @GetMapping("/ban")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getBanPage(Model model) {
        log.info("Getting ban page");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return "ban";
    }


    @PostMapping("/ban")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String banAllUsers() {
        log.info("Banning all users");
        List<User> userList = userRepository.findAll(hasRole(User.Role.USER));
        userList.forEach(user -> user.setState(User.State.BANNED));
        userRepository.saveAll(userList);
        return "redirect:/hello";
    }

    @GetMapping("/admin-links")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAdminLinksPage(Model model) {
        log.info("Getting admin links page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return "adminLinksPage";
    }
}