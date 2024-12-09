package com.tarkhan.backend.model.author;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CreateAuthorDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String biography;
    private String imageUrl;
}

