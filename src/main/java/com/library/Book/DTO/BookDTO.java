package com.library.Book.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class BookDTO {

    @AllArgsConstructor
    @Builder
    @lombok.Getter
    @Data
    public static class PostInput {
        public String name;
        public Integer pages;
    }
    
}
