package com.tarkhan.backend.entity;

import com.tarkhan.backend.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    @NotBlank(message = "Title cannot be empty")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Column(nullable = false, length = 2000)
    private String description;

    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Double rating;

    private int pageNumber;
    private int year;
    private String language;
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "book_tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @NotNull(message = "Author cannot be null")
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @NotNull(message = "Publisher cannot be null")
    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @NotNull(message = "Category cannot be null")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
