package com.library.Book.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.Book.model.BookEntity;

@Repository

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    public BookEntity findByNameAndPages(String name, Integer pages);

}
