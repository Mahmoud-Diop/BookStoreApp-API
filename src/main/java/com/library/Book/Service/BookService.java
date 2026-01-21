package com.library.Book.Service;

import org.springframework.stereotype.Service;

import com.library.Book.Persistence.BookRepository;
import com.library.Book.model.BookEntity;

@Service
public class BookService {

    final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String createBook(String name, Integer pages) {
        if(name == null || pages == null){
            return "Invalid input";
        }

        BookEntity newBook = BookEntity.builder()
                .name(name)
                .pages(pages)
                .build();

        BookEntity exists = bookRepository.findByNameAndPages(name, pages);
        if (exists != null) {
            return "Book already exists";
        } else {
            bookRepository.save(newBook);
            return "book saved successfully";
        }

    }

}
