package ru.itis.orisjavaproject.Controllers.NewsController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.orisjavaproject.Entities.News;
import ru.itis.orisjavaproject.Entities.Post;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.ImageService;
import ru.itis.orisjavaproject.Services.NewsService.NewsService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
@Slf4j
public class NewsController {
    private final NewsService newsService;
    private final ImageService imageService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAllNews(Model model) {
        log.info("Getting all news");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<News> newsList = newsService.getAllNews();
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("newsList", newsList);
        return "news/list";
    }

    @GetMapping("/{id}")
    public String getNewsById(@PathVariable UUID id, Model model) {
        log.info("Getting news by id: {}", id);
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        News news = newsService.getNewsById(id).orElse(null);
        model.addAttribute("news", news);
        return "news/details";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createNewsForm(Model model) {
        log.info("Showing create news form");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return "news/form";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createNews(@ModelAttribute News news) {
        log.info("Creating news with title: {}", news.getTitle());
        newsService.saveNews(news);
        return "redirect:/news";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateNewsForm(@PathVariable UUID id, Model model) {
        log.info("Showing update news form for id: {}", id);
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        model.addAttribute("newsId", id);
        return "news/FormUpd";
    }

    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateNews(@ModelAttribute News news) {
        log.info("Updating news with id: {}", news.getId());
        newsService.updateNews(news);
        return "redirect:/news";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteNews(@PathVariable UUID id) {
        log.info("Deleting news with id: {}", id);
        newsService.deleteNews(id);
        return "redirect:/news";
    }
}