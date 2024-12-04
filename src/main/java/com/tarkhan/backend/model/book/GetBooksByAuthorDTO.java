package com.tarkhan.backend.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBooksByAuthorDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private String imageUrl;
    private int pageNumber;
    private int year;
    private String language;
    private String authorName;
    private String publisherName;
    private String genreName;
}
