package ru.itis.orisjavaproject.Converters;

import ru.itis.orisjavaproject.Entities.BookEntitys.Book;
import ru.itis.orisjavaproject.dto.BookDto;

public class BookConverter {
    public static BookDto convertToDto(Book book) {
        if (book == null) {
            return null;
        }
        return new BookDto(book.getTitle(), book.getSubjects(), book.getAuthors());
    }
}