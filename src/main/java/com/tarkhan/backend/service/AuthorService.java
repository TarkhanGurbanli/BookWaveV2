package com.tarkhan.backend.service;

import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.author.CreateAuthorDTO;
import com.tarkhan.backend.model.author.GetAuthorWithBooksDTO;
import com.tarkhan.backend.model.author.UpdateAuthorDTO;

import java.util.List;

public interface AuthorService {
    void createAuthor(CreateAuthorDTO createAuthorDTO);
    void updateAuthor(Long id, UpdateAuthorDTO updateAuthorDTO);
    void deleteAuthor(Long id);
    List<AuthorDTO> getAllAuthors();
    List<GetAuthorWithBooksDTO> getAllAuthorsWithBooks();
    GetAuthorWithBooksDTO getAuthorBooksById(Long authorId);
    List<AuthorDTO> getAuthorsByPage(int pageNumber, int pageSize);
    AuthorDTO getByIdAuthor(Long id);
}
