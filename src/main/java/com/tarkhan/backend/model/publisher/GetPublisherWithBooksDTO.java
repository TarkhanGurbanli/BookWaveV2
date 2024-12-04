package com.tarkhan.backend.model.publisher;

import com.tarkhan.backend.model.book.BookDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetPublisherWithBooksDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private List<BookDTO> books;
}
