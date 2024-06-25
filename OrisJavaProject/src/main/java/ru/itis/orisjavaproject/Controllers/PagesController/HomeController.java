package ru.itis.orisjavaproject.Controllers.PagesController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.orisjavaproject.Entities.News;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.ImageService;
import ru.itis.orisjavaproject.Services.NewsService.NewsService;
import ru.itis.orisjavaproject.Services.UserService.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final NewsService newsService;
    private final ImageService imageService;



    @GetMapping("/home")
    public String home(Model model) {
        log.info("Handling GET request for /home");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("newsList", newsList);
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return "index";
    }
}