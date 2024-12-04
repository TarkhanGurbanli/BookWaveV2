package com.tarkhan.backend.model.author;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String nationality;
    private String biography;
    private String imageUrl;
}
