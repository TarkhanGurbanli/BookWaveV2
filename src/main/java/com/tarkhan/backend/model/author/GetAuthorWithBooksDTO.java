package com.tarkhan.backend.model.author;

import com.tarkhan.backend.model.book.BookDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GetAuthorWithBooksDTO {
    private Long id;
    private String name;
    private String biography;
    private String imageUrl;
    private List<BookDTO> books;
}
