package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Author;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.mapping.AuthorMapping;
import com.tarkhan.backend.model.author.AuthorDTO;
import com.tarkhan.backend.model.author.CreateAuthorDTO;
import com.tarkhan.backend.model.author.GetAuthorWithBooksDTO;
import com.tarkhan.backend.model.author.UpdateAuthorDTO;
import com.tarkhan.backend.repository.AuthorRepository;
import com.tarkhan.backend.service.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapping authorMapping;
    private final ModelMapper modelMapper;

    @Override
    public void createAuthor(CreateAuthorDTO createAuthorDTO) {
        Author author = modelMapper.map(createAuthorDTO, Author.class);
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Long id, UpdateAuthorDTO updateAuthorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "ID", id));

        modelMapper.map(updateAuthorDTO, author);
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Author", "ID", id));

        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetAuthorWithBooksDTO> getAllAuthorsWithBooks() {
        List<Author> authors = authorRepository.findAll();

        List<GetAuthorWithBooksDTO> dtos = authors.stream()
                .map(authorMapping::getAuthorWithBooksDTO)
                .collect(Collectors.toList());

        return dtos;
    }

    @Override
    public GetAuthorWithBooksDTO getAuthorBooksById(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "ID", authorId));

        return authorMapping.getAuthorWithBooksDTO(author);
    }

    @Override
    public List<AuthorDTO> getAuthorsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Author> authors = authorRepository.findAll(pageable).getContent();

        return authors.stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public AuthorDTO getByIdAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "ID", id));

        return modelMapper.map(author, AuthorDTO.class);
    }
}