package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.*;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.mapping.BookMapping;
import com.tarkhan.backend.model.book.*;
import com.tarkhan.backend.repository.*;
import com.tarkhan.backend.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final TagRepository tagRepository;
    private final BookMapping bookMapping;
    private final ModelMapper modelMapper;

    @Override
    public void createBook(CreateBookDTO createBookDTO){
        Book book = new Book();

        book.setTitle(createBookDTO.getTitle());
        book.setDescription(createBookDTO.getDescription());
        book.setRating(createBookDTO.getRating());
        book.setImageUrl(createBookDTO.getImageUrl());

        Author author = authorRepository.findById(createBookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "ID", createBookDTO.getAuthorId()));

        Publisher publisher = publisherRepository.findById(createBookDTO.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", "ID", createBookDTO.getPublisherId()));

        Category category = categoryRepository.findById(createBookDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(" ", "ID", createBookDTO.getCategoryId()));

        List<Tag> tags = tagRepository.findAllById(createBookDTO.getTagIds());
        if (tags.isEmpty() && !createBookDTO.getTagIds().isEmpty()) {
            throw new ResourceNotFoundException("Tag", "IDs", createBookDTO.getTagIds());
        }

        book.setTags(tags);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);

        bookRepository.save(book);
    }

    @Override
    public void updateBook(Long id, UpdateBookDTO updateBookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", id));

        modelMapper.map(updateBookDTO, book);


        Author author = authorRepository.findById(updateBookDTO.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", "ID", updateBookDTO.getAuthorId()));

        Publisher publisher = publisherRepository.findById(updateBookDTO.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher", "ID", updateBookDTO.getPublisherId()));

        Category category = categoryRepository.findById(updateBookDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", updateBookDTO.getCategoryId()));

        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategory(category);

        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", id));
        bookRepository.delete(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getPageAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Book> books = bookRepository.findAll(pageable).getContent();
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetBookWithCategoriesDTO> getBooksWithCategory() {
        List<Book> books = bookRepository.findAll();

        List<GetBookWithCategoriesDTO> dtos = books.stream()
                .map(bookMapping::toBookWithGenres)
                .collect(Collectors.toList());

        return dtos;
    }


    @Override
    public List<GetBookWithPublishersDTO> getBooksWithPublisher() {
        List<Book> books = bookRepository.findAll();
        List<GetBookWithPublishersDTO> dtos = books.stream()
                .map(bookMapping::toBookWithPublishers)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<GetBookWithAuthorsDTO> getBooksWithAuthor() {
        List<Book> books = bookRepository.findAll();
        List<GetBookWithAuthorsDTO> dtos = books.stream()
                .map(bookMapping::toBookWithAuthors)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public GetBookWithCategoriesDTO getBookByCategory(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", bookId));
        return bookMapping.toBookWithGenres(book);
    }

    @Override
    public GetBookWithPublishersDTO getBookByPublisher(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", bookId));
        return bookMapping.toBookWithPublishers(book);
    }

    @Override
    public GetBookWithAuthorsDTO getBookByAuthor(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", bookId));
        return bookMapping.toBookWithAuthors(book);
    }

    @Override
    public List<GetBookWithDetailsDTO> getBookWithDetails() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapping::getBookWithDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GetBookWithDetailsDTO getBookByDetails(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", bookId));

        return bookMapping.getBookWithDetailsDTO(book);
    }


    @Override
    public BookDTO getByIdBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ID", id));

        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    public List<GetBooksByCategoryDTO> getBooksByCategory(String genreName, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        List<Book> booksPage = bookRepository.findBooksByCategoryName(genreName, pageable);

        if(booksPage.isEmpty()){
            throw new ResourceNotFoundException("Book", "Genre Name", genreName);
        }

        return booksPage.stream()
                .map((book -> modelMapper.map(book, GetBooksByCategoryDTO.class)))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetBooksByAuthorDTO> getBooksByAuthor(String authorName, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        List<Book> booksPage = bookRepository.findBooksByAuthorName(authorName, pageable);

        if(booksPage.isEmpty()){
            throw new ResourceNotFoundException("Book", "Author Name", authorName);
        }

        return booksPage.stream()
                .map((book -> modelMapper.map(book, GetBooksByAuthorDTO.class)))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetBooksByPublisherDTO> getBooksByPublisher(String publisherName, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        List<Book> booksPage = bookRepository.findBooksByPublisherName(publisherName, pageable);

        if(booksPage.isEmpty()){
            throw new ResourceNotFoundException("Book", "Publisher Name", publisherName);
        }

        return booksPage.stream()
                .map((book -> modelMapper.map(book, GetBooksByPublisherDTO.class)))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooks(String keyword, String filterBy, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Book> books;

        switch (filterBy.toLowerCase()) {
            case "author":
                books = bookRepository.findByAuthor_NameContainingIgnoreCase(keyword, pageable);
                break;
            case "genre":
                books = bookRepository.findByCategory_NameContainingIgnoreCase(keyword, pageable);
                break;
            case "publisher":
                books = bookRepository.findByPublisher_NameContainingIgnoreCase(keyword, pageable);
                break;
            case "title":
            default:
                books = bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
                break;
        }

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Book", "Keyword", keyword);
        }

        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }
}