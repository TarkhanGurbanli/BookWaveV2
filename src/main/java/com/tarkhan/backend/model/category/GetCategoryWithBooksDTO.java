package com.tarkhan.backend.model.category;

import com.tarkhan.backend.model.book.BookDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetCategoryWithBooksDTO {
    private Long id;
    private String name;
    private List<BookDTO> books;
}