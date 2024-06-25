package ru.itis.orisjavaproject.Controllers.BooksControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.orisjavaproject.Converters.BookConverter;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.Security.UserDetailsImpl;
import ru.itis.orisjavaproject.Services.BookService;
import ru.itis.orisjavaproject.Services.ImageService;
import ru.itis.orisjavaproject.dto.BookDto;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BookController {
    private final BookService bookService;
    private final ImageService imageService;

    @GetMapping("/books/user/{userId}")
    public String getAllBooksByUser(Model model) {
        log.info("Getting all books for user");
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        return "Books/books";
    }

    @GetMapping("/books/user/{userId}/filtered")
    public String getFilteredBooksByUser(@PathVariable UUID userId,
                                         @RequestParam(required = false) String title,
                                         @RequestParam(required = false) String author,
                                         @RequestParam(required = false) String subject,
                                         Model model) {
        log.info("Getting filtered books for user with id: {}, title: {}, author: {}, subject: {}",
                userId, title, author, subject);
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        List<Book> books = bookService.getFilteredBooksByUser(userId, title, author, subject);

        model.addAttribute("books", books);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("userId", userDetails.getUser().getId());

        return "Books/filteredBookShow";
    }

    @PostMapping("/books/user/{userId}/delete/{bookId}")
    public String deleteBookLink(@PathVariable UUID userId, @PathVariable UUID bookId) {
        log.info("Deleting book link for user with id: {} and book with id: {}", userId, bookId);
        bookService.deleteBookLinkById(userId, bookId);
        return "redirect:/users/{userId}";
    }

    @GetMapping("/books/{bookId}")
    public String getBookById(@PathVariable UUID bookId, Model model) {
        log.info("Getting book by id: {}", bookId);
        model.addAttribute("image",
                (String) Base64.encodeBase64String(imageService.getRandomImage().get().getData()));
        Book book = bookService.getBookById(bookId);
        BookDto bookDto = BookConverter.convertToDto(book);
        model.addAttribute("book", bookDto);
        return "Books/bookDetails";
    }
}
