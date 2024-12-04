package com.tarkhan.backend.service;

import com.tarkhan.backend.model.category.CreateCategoryDTO;
import com.tarkhan.backend.model.category.CategoryDTO;
import com.tarkhan.backend.model.category.GetCategoryWithBooksDTO;
import com.tarkhan.backend.model.category.UpdateCategoryDTO;

import java.util.List;

public interface CategoryService {
    void createCategory(CreateCategoryDTO createCategoryDTO);
    void updateCategory(Long id, UpdateCategoryDTO updateCategoryDTO);
    void deleteCategory(Long id);
    CategoryDTO getCategoryById(Long id);
    CategoryDTO getCategoryByName(String name);
    List<CategoryDTO> getAllCategories();
    List<CategoryDTO> getCategoriesByPage(int pageNumber, int pageSize);
    List<GetCategoryWithBooksDTO> getCategoriesWithBooks();
    GetCategoryWithBooksDTO getCategoryBooksById(Long categoryId);
}
