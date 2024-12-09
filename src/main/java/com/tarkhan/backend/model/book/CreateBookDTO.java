package com.tarkhan.backend.model.book;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class CreateBookDTO {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating must not exceed 5")
    private Double rating;

    private String imageUrl;
    private int pageNumber;
    private int year;
    private String language;
    private Long authorId;
    private Long publisherId;

    @NotNull(message = "Genre cannot be null")
    private Long categoryId;
    private List<Long> tagIds;
}
