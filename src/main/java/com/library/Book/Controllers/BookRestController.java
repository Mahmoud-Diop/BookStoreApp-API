package com.library.Book.Controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.library.Book.DTO.BookDTO;
import com.library.Book.Service.BookService;
import com.library.Book.model.BookEntity;
import com.library.Book.model.exceptions.BookCreationException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/books")
public class BookRestController {

    final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BookDTO.PostOutput createBook(@Validated @RequestBody BookDTO.PostInput input) throws BookCreationException {
        BookEntity book = bookService.createBook(input.getName(), input.getPages());
        return BookDTO.PostOutput.builder()
                .id(book.getId())
                .name(book.getName())
                .pages(book.getPages())
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")

    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")

    public BookEntity getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public BookDTO.PostOutput updateBook(@PathVariable Long id,
            @Validated @RequestBody BookDTO.PostInput input) throws BookCreationException {
        BookEntity book = bookService.updateBook(id, input.getName(), input.getPages());
        return BookDTO.PostOutput.builder()
                .id(book.getId())
                .name(book.getName())
                .pages(book.getPages())
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') ")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
