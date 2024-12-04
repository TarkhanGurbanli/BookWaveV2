package com.tarkhan.backend.mapping;

import com.tarkhan.backend.entity.Book;
import com.tarkhan.backend.entity.Category;
import com.tarkhan.backend.model.book.BookDTO;
import com.tarkhan.backend.model.category.GetCategoryWithBooksDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapping {
    public GetCategoryWithBooksDTO toGenreWithBooksDTO(Category category) {
        GetCategoryWithBooksDTO dto = new GetCategoryWithBooksDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());

        List<Book> books = category.getBooks();

        List<BookDTO> bookDTOs = new ArrayList<>();

        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
                BookDTO bookDTO = new BookDTO();
                bookDTO.setId(book.getId());
                bookDTO.setTitle(book.getTitle());
                bookDTO.setDescription(book.getDescription());
                bookDTO.setRating(book.getRating());
                bookDTO.setAuthorId(category.getId());
                bookDTO.setPublisherId(book.getPublisher().getId());
                bookDTO.setGenreId(book.getCategory().getId());

                bookDTO.setImageUrl(book.getImageUrl());
                bookDTOs.add(bookDTO);
            }
        }
        dto.setBooks(bookDTOs);
        return dto;
    }
}