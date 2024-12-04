package com.tarkhan.backend.model.book;

import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.category.CategoryDTO;
import com.tarkhan.backend.model.publisher.PublisherDTO;
import lombok.Data;

@Data
public class GetBookWithDetailsDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private String imageUrl;
    private int pageNumber;
    private int year;
    private String language;
    private AuthorDTO author;
    private CategoryDTO genre;
    private PublisherDTO publisher;
}