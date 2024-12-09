package com.tarkhan.backend.service;

import com.tarkhan.backend.model.book.*;

import java.util.List;

public interface BookService {
    void createBook(CreateBookDTO createBookDTO);
    void updateBook(Long id, UpdateBookDTO updateBookDTO);
    void deleteBook(Long id);
    List<BookDTO> getAllBooks();
    List<BookDTO> getPageAllBooks(int pageNumber, int pageSize);


    List<GetBookWithCategoriesDTO> getBooksWithCategory();
    List<GetBookWithPublishersDTO> getBooksWithPublisher();
    List<GetBookWithAuthorsDTO> getBooksWithAuthor();
    GetBookWithCategoriesDTO getBookByCategory(Long bookId);
    GetBookWithPublishersDTO getBookByPublisher(Long bookId);
    GetBookWithAuthorsDTO getBookByAuthor(Long bookId);
    List<GetBookWithDetailsDTO> getBookWithDetails();
    GetBookWithDetailsDTO getBookByDetails(Long bookId);
    BookDTO getByIdBook(Long id);
    List<GetBooksByCategoryDTO> getBooksByCategory(String genreName, int pageNumber, int pageSize);
    List<GetBooksByAuthorDTO> getBooksByAuthor(String authorName, int pageNumber, int pageSize);
    List<GetBooksByPublisherDTO> getBooksByPublisher(String publisherName, int pageNumber, int pageSize);
    List<BookDTO> searchBooks(String keyword, String filterBy, int pageNumber, int pageSize);
}
