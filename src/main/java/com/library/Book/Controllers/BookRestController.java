package com.library.Book.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.Book.DTO.BookDTO;
import com.library.Book.Service.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/books")
public class BookRestController {

    final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

   
    @PostMapping
    public String createBook(@RequestBody BookDTO.PostInput input) {

        return bookService.createBook(input.getName(), input.getPages());
        
        
    }
    


}
