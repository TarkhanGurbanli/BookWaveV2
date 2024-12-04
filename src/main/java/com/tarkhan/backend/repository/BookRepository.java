package com.tarkhan.backend.repository;

import com.tarkhan.backend.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByCategoryName(String genreName, Pageable pageable);
    List<Book> findBooksByAuthorName(String authorName, Pageable pageable);
    List<Book> findBooksByPublisherName(String publisherName, Pageable pageable);
    List<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    List<Book> findByAuthor_NameContainingIgnoreCase(String authorName, Pageable pageable);
    List<Book> findByCategory_NameContainingIgnoreCase(String cName, Pageable pageable);
    List<Book> findByPublisher_NameContainingIgnoreCase(String publisherName, Pageable pageable);
}
