package com.example.first_project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.first_project.model.BookEntity;

@RestController
public class BookRestController {

    final BookRepository bookRepository;

    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public String getBooks(@RequestParam String name, @RequestParam Integer pages) {

        BookEntity newBook = BookEntity.builder()
                .name(name)
                .pages(pages)
                .build();

        boolean exists = bookRepository.findByNameAndPages(name, pages) != null;
        if (exists) {
            return "Book already exists";
        } else {
            bookRepository.save(newBook);
       }

        return "List of books";
    }

}
