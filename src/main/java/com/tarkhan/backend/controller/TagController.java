package com.tarkhan.backend.controller;

import com.tarkhan.backend.constants.Constants;
import com.tarkhan.backend.model.ResponseModel;
import com.tarkhan.backend.model.tag.CreateTagDTO;
import com.tarkhan.backend.model.tag.TagDTO;
import com.tarkhan.backend.model.tag.UpdateTagDTO;
import com.tarkhan.backend.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    @Operation(summary = "Get all tags",
            description = "Retrieves a list of all tags the user intends to read.")
    public ResponseEntity<List<TagDTO>> getTags(){
        List<TagDTO> tagDTOS = tagService.getAll();
        return ResponseEntity.ok(tagDTOS);
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    @Operation(summary = "Get tags by page",
            description = "Retrieves a paginated list of tags the user intends to read.")
    public ResponseEntity<List<TagDTO>> tagsByPage(
            @Parameter(description = "Page number to retrieve") @RequestParam int pageNumber,
            @Parameter(description = "Number of items per page") @RequestParam int pageSize) {
        List<TagDTO> tagDTOS = tagService.getTagsByPage(pageNumber, pageSize);
        return ResponseEntity.ok(tagDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tag by ID",
            description = "Retrieves a tag by its ID.")
    public ResponseEntity<TagDTO> tagsById(
            @Parameter(description = "ID of the tag to retrieve") @PathVariable Long id) {
        TagDTO tagDTO = tagService.getById(id);
        return ResponseEntity.ok(tagDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new tag")
    public ResponseEntity<ResponseModel> createTag(
            @RequestBody @Valid CreateTagDTO request) {
        tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_CREATED,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing tag book")
    public ResponseEntity<ResponseModel> updateReadBook(
            @Parameter(description = "ID of the tag to update") @PathVariable @Valid Long id,
            @RequestBody @Valid UpdateTagDTO request) {
        tagService.updateTag(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_UPDATE_SUCCESSFUL
                )
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tag")
    public ResponseEntity<ResponseModel> deleteTag(
            @Parameter(description = "ID of the tag to delete") @PathVariable @Valid Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ResponseModel(
                        Constants.STATUS_NO_CONTENT,
                        Constants.MESSAGE_DELETE_SUCCESSFUL
                )
        );
    }
}
