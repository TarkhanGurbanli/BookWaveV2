package com.tarkhan.backend.mapping;

import com.tarkhan.backend.entity.Author;
import com.tarkhan.backend.entity.Book;
import com.tarkhan.backend.entity.Category;
import com.tarkhan.backend.entity.Publisher;
import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.book.GetBookWithAuthorsDTO;
import com.tarkhan.backend.model.book.GetBookWithDetailsDTO;
import com.tarkhan.backend.model.book.GetBookWithGenresDTO;
import com.tarkhan.backend.model.book.GetBookWithPublishersDTO;
import com.tarkhan.backend.model.category.CategoryDTO;
import com.tarkhan.backend.model.publisher.PublisherDTO;
import com.tarkhan.backend.repository.AuthorRepository;
import com.tarkhan.backend.repository.BookRepository;
import com.tarkhan.backend.repository.CategoryRepository;
import com.tarkhan.backend.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BookMapping {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

//    public GetBookWithGenresDTO toBookWithGenres(Book book) {
//        GetBookWithGenresDTO dto = new GetBookWithGenresDTO();
//        dto.setId(book.getId());
//        dto.setTitle(book.getTitle());
//        dto.setDescription(book.getDescription());
//        dto.setRating(book.getRating());
//
//        Book existingBook = bookRepository.findById(book.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", book.getId()));
//
//        Genre genre = existingBook.getGenre();
//        List<GenreDTO> genreDTOs = new ArrayList<>();
//
//            GenreDTO genreDTO = new GenreDTO();
//            genreDTO.setId(genre.getId());
//            genreDTO.setName(genre.getName());
//            genreDTOs.add(genreDTO);
//
//        dto.setGenres(genreDTOs);
//
//            dto.setImageId(existingBook.getCoverImage().getId());
//
//        return dto;
//    }

    public GetBookWithGenresDTO toBookWithGenres(Book book) {
        GetBookWithGenresDTO dto = new GetBookWithGenresDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setRating(book.getRating());
        dto.setImageUrl(book.getImageUrl());
        dto.setPageNumber(book.getPageNumber());
        dto.setLanguage(book.getLanguage());
        dto.setYear(book.getYear());

        Category category = book.getCategory();
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            dto.setGenre(categoryDTO);
            dto.setImageUrl(book.getImageUrl());
        return dto;
    }


    public GetBookWithPublishersDTO toBookWithPublishers(Book book) {
        GetBookWithPublishersDTO dto = new GetBookWithPublishersDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setRating(book.getRating());
        dto.setPageNumber(book.getPageNumber());
        dto.setLanguage(book.getLanguage());
        dto.setYear(book.getYear());

        Publisher publisher = book.getPublisher();
            PublisherDTO publisherDTO = new PublisherDTO();
            publisherDTO.setId(publisher.getId());
            publisherDTO.setName(publisher.getName());


        dto.setPublisher(publisherDTO);
        dto.setImageUrl(book.getImageUrl());
        return dto;
    }

    public GetBookWithAuthorsDTO toBookWithAuthors(Book book) {
        GetBookWithAuthorsDTO dto = new GetBookWithAuthorsDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setRating(book.getRating());
        dto.setPageNumber(book.getPageNumber());
        dto.setLanguage(book.getLanguage());
        dto.setYear(book.getYear());

        Author author = book.getAuthor();
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setNationality(author.getNationality());
        authorDTO.setBiography(author.getBiography());
        authorDTO.setBirthDate(author.getBirthDate());
        authorDTO.setImageUrl(author.getImageUrl());

        dto.setAuthor(authorDTO);
        dto.setImageUrl(book.getImageUrl());
        return dto;
    }

    public GetBookWithDetailsDTO getBookWithDetailsDTO(Book book) {
        GetBookWithDetailsDTO dto = new GetBookWithDetailsDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setRating(book.getRating());

        // Author
        Author author = book.getAuthor();
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setNationality(author.getNationality());
        authorDTO.setBiography(author.getBiography());
        authorDTO.setBirthDate(author.getBirthDate());
        authorDTO.setImageUrl(author.getImageUrl());
        dto.setAuthor(authorDTO);

        // Genre
        Category category = book.getCategory();
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        dto.setGenre(categoryDTO);

        // Publisher
        Publisher publisher = book.getPublisher();
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setId(publisher.getId());
        publisherDTO.setName(publisher.getName());
        dto.setPublisher(publisherDTO);

        dto.setImageUrl(book.getImageUrl());
        return dto;
    }
}
