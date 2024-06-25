package ru.itis.orisjavaproject.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.Repositories.BookRepository;
import ru.itis.orisjavaproject.exception.NotFoundServiceException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> getAllBooksByUser(UUID userId) {
        return bookRepository.findAllBooksByUserid(userId);
    }

    public List<Book> getFilteredBooksByUser(UUID userId, String title, String author, String subject) {
        List<Book> books = bookRepository.findAllBooksByUserid(userId);

        if (title != null && !title.isEmpty()) {
            books = books.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }

        else if (author != null && !author.isEmpty()) {
            books = books.stream()
                    .filter(book -> book.getAuthors().stream()
                            .anyMatch(a -> a.getName().toLowerCase().contains(author.toLowerCase())))
                    .collect(Collectors.toList());
        }

        else if (subject != null && !subject.isEmpty()) {
            books = books.stream()
                    .filter(book -> book.getSubjects().stream()
                            .anyMatch(s -> s.getName().toLowerCase().contains(subject.toLowerCase())))
                    .collect(Collectors.toList());
        }

        return books;

    }



    public void deleteBookLinkById(UUID userId, UUID bookId) {
        bookRepository.deleteBookLinkById(userId, bookId);
    }

    public Book getBookById(UUID bookId) {
         return bookRepository.findById(bookId).orElseThrow(() -> new NotFoundServiceException(bookId.toString()));
    }

}