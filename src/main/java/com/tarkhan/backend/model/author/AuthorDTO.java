package com.tarkhan.backend.model.author;

import lombok.Data;

@Data
public class AuthorDTO {
    private Long id;
    private String name;
    private String biography;
    private String imageUrl;
}
