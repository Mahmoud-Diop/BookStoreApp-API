package com.library.Book.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
public class BookDTO {

    @AllArgsConstructor
    @Builder
    @Data
    public static class PostInput {
        @NotNull @NotBlank
        public String name;
            
        @NotNull
        public Integer pages;
    }

    @AllArgsConstructor
    @Builder
    @Data

    public static class PostOutput {
        public Long id;
        public String name;
        public Integer pages;
    }

}
