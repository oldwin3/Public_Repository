package ru.itis.orisjavaproject.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.Entities.EntityesForMoodTest.Option;
import ru.itis.orisjavaproject.Entities.EntityesForMoodTest.Question;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.ImageService;
import ru.itis.orisjavaproject.Services.TestService;

import java.util.*;

@Controller
@SessionAttributes({"recommendedBooks", "currentBookIndex", "book"})
@RequiredArgsConstructor
public class TestController {

    private final TestService moodTestService;
    private final ImageService imageService;



    @GetMapping("/mood-test/{userId}")
    public String showTest(Model model) {
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        List<Question> questions = moodTestService.getQuestions();
        model.addAttribute("questions", questions);
        return "test";
    }

    @GetMapping("/test")
    public String show(Model model) {
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("userId", userDetails.getUser().getId());
        return "mainTest";
    }

    @GetMapping("/mood-test/random")
    public String showRandomBook(Model model) {
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String bookToSave = (String) model.getAttribute("book");;
        if (moodTestService.bookSaved(userDetails.getUser().getId(), bookToSave)){
            model.addAttribute("bookSaved", true);
        }
        else {
            model.addAttribute("bookSaved", false);
        }
        return "RandomBookShow";
    }
    @PostMapping("/test")
    public String RandomBook(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("book", moodTestService.getBooksBySubject(userDetails.getUser().getId()).
                getTitle());
        return "redirect:/mood-test/random";
    }

    @PostMapping("/mood-test")
    public String saveBookToLibrary2(Model model) {
        Book bookToSave = (Book) model.getAttribute("book");;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        moodTestService.saveBookToLibrary(userDetails.getUser().getId(), bookToSave.getId());
        return "redirect:/test";
    }


    @PostMapping("/mood-test/{userId}")
    public String submitTest(@PathVariable UUID userId, HttpServletRequest request, Model model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<Option> selectedOptions = new ArrayList<>();

        int counter = 0;
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if (counter < 1) {
                counter++;
                continue;
            }

            String[] values = entry.getValue();
            if (values != null && values.length > 0) {
                Option selectedOption = new Option();
                selectedOption.setText(values[0]);
                selectedOptions.add(selectedOption);

            }
        }


        Map<String, Double> mood = moodTestService.determineMood(selectedOptions);
        List<Book> recommendedBooks = moodTestService.returnBooksForUser(mood, userId);
        model.addAttribute("recommendedBooks", recommendedBooks);
        model.addAttribute("currentBookIndex", 0);

        return "redirect:/mood-test/{userId}" + "/0";
    }

    @GetMapping("/mood-test/{userId}/{bookIndex}")
    public String showBook(@PathVariable UUID userId, @PathVariable int bookIndex, Model model) {
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        List<Book> recommendedBooks = (List<Book>) model.getAttribute("recommendedBooks");
        if (bookIndex > recommendedBooks.size() - 1 || bookIndex < 0){
            return "error";
        }

        if (moodTestService.bookSaved(userId, recommendedBooks.get(bookIndex).getTitle())){
            model.addAttribute("bookSaved", true);
        }
        else {
            model.addAttribute("bookSaved", false);
        }

        model.addAttribute("currentBookIndex", bookIndex);
        return "result";
    }

    @PostMapping("/mood-test/{userId}/{bookIndex}")
    public String saveBookToLibrary(@PathVariable UUID userId, @RequestParam int bookIndex, Model model) {
        List<Book> recommendedBooks = (List<Book>) model.getAttribute("recommendedBooks");
        if (bookIndex < 0 || bookIndex >= recommendedBooks.size()) {
            return "error";
        }
        Book bookToSave = recommendedBooks.get(bookIndex);

        moodTestService.saveBookToLibrary(userId, bookToSave.getId());

        return "redirect:/mood-test/{userId}/{bookIndex}";
    }
}