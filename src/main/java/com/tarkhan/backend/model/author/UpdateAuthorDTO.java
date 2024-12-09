package com.tarkhan.backend.model.author;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UpdateAuthorDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String biography;
    private String imageUrl;
}
