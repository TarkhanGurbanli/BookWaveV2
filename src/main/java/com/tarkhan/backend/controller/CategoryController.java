package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.category.CreateCategoryDTO;
import com.tarkhan.backend.model.category.CategoryDTO;
import com.tarkhan.backend.model.category.GetCategoryWithBooksDTO;
import com.tarkhan.backend.model.category.UpdateCategoryDTO;
import com.tarkhan.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "Category Management")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(value = "/admin")
    @Operation(summary = "Create Category", description = "Creates a new category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ResponseModel> createGenre(
            @Valid @RequestBody CreateCategoryDTO dto
    ) {
        categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/admin/{id}")
    @Operation(summary = "Update Category", description = "Updates an existing category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ResponseModel> updateGenre(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody UpdateCategoryDTO dto
    ) {
        categoryService.updateCategory(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Delete Category", description = "Deletes a category by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<ResponseModel> deleteGenre(
            @Valid @PathVariable("id") Long id
    ) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }

    @GetMapping
    @Operation(summary = "Get All Categories", description = "Retrieves a list of all categories.")
    public ResponseEntity<List<CategoryDTO>> getAllGenres() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping("/with-books")
    @Operation(summary = "Get Categories with Books",
            description = "Retrieves categories along with their associated books.")
    public ResponseEntity<List<GetCategoryWithBooksDTO>> getGenresWithBooks() {
        List<GetCategoryWithBooksDTO> categoriesWithBooks = categoryService.getCategoriesWithBooks();
        return ResponseEntity.status(HttpStatus.OK).body(categoriesWithBooks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Category by ID",
            description = "Retrieves a category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> getGenreById(
            @Valid @PathVariable("id") Long id
    ) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping("/{id}/books")
    @Operation(summary = "Get Books by Category ID",
            description = "Retrieves books associated with a specific category.")
    public ResponseEntity<GetCategoryWithBooksDTO> getGenreBooksById(
            @Valid @PathVariable("id") Long id
    ) {
        GetCategoryWithBooksDTO categoryWithBooks = categoryService.getCategoryBooksById(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryWithBooks);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get Category by Name",
            description = "Retrieves a category by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryDTO> getGenreByName(
            @Valid @PathVariable("name") String name
    ) {
        CategoryDTO category = categoryService.getCategoryByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    @Operation(summary = "Get Paginated Categories",
            description = "Retrieves a paginated list of categories.")
    public ResponseEntity<List<CategoryDTO>> getGenresByPage(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize
    ) {
        List<CategoryDTO> categories = categoryService.getCategoriesByPage(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
