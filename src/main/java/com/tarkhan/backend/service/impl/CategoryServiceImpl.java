package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Category;
import com.tarkhan.backend.exception.BookWaveApiException;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.mapping.CategoryMapping;
import com.tarkhan.backend.model.category.CreateCategoryDTO;
import com.tarkhan.backend.model.category.CategoryDTO;
import com.tarkhan.backend.model.category.GetCategoryWithBooksDTO;
import com.tarkhan.backend.model.category.UpdateCategoryDTO;
import com.tarkhan.backend.repository.CategoryRepository;
import com.tarkhan.backend.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapping categoryMapping;
    private final ModelMapper modelMapper;

    @Override
    public void createCategory(CreateCategoryDTO createCategoryDTO) {
        Category category = modelMapper.map(createCategoryDTO, Category.class);

        if(categoryRepository.existsByName(category.getName())) {
                throw new BookWaveApiException("Category already exists");
        }

        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long id, UpdateCategoryDTO updateCategoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));

        boolean existName = categoryRepository.existsByName(updateCategoryDTO.getName());
        if (existName) {
            throw new BookWaveApiException("Category already exists");
        }

        modelMapper.map(updateCategoryDTO, category);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));

        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", id));

        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "NAME", name));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(genre -> modelMapper
                        .map(genre, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getCategoriesByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        return categories.stream()
                .map(genre -> modelMapper.map(genre, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetCategoryWithBooksDTO> getCategoriesWithBooks() {
        List<Category> dtos = categoryRepository.findAll();
        return dtos.stream()
                .map(categoryMapping::toGenreWithBooksDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GetCategoryWithBooksDTO getCategoryBooksById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
        return categoryMapping.toGenreWithBooksDTO(category);
    }
}
