package com.library.Book.Service;

import org.springframework.stereotype.Service;

import com.library.Book.Persistence.BookRepository;
import com.library.Book.model.BookEntity;
import com.library.Book.model.exceptions.BookCreationException;

import java.util.List;
@Service
public class BookService {

    final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity createBook(String name, Integer pages) throws BookCreationException {
        if (name == null || name.isBlank()) {
            throw new BookCreationException("Name cannot be null or empty");
        }
        BookEntity exists = bookRepository.findByNameAndPages(name, pages);
        if (exists != null) {
            throw new BookCreationException("Book already exists");
        }
        BookEntity newBook = BookEntity.builder()
                .name(name)
                .pages(pages)
                .build();
        return bookRepository.save(newBook);
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookCreationException("Book not found"));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
