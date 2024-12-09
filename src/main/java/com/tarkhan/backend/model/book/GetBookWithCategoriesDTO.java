package com.tarkhan.backend.model.book;

import com.tarkhan.backend.model.category.CategoryDTO;
import lombok.Data;

@Data
public class GetBookWithCategoriesDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private String imageUrl;
    private int pageNumber;
    private int year;
    private String language;
    private CategoryDTO category;
}
