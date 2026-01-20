package com.example.first_project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.first_project.model.BookEntity;

@Repository

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    public BookEntity findByNameAndPages(String name, Integer pages);

}
