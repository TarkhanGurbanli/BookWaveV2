package com.tarkhan.backend.service;

import com.tarkhan.backend.model.tag.CreateTagDTO;
import com.tarkhan.backend.model.tag.TagDTO;
import com.tarkhan.backend.model.tag.UpdateTagDTO;

import java.util.List;

public interface TagService {
    void createTag(CreateTagDTO dto);
    void updateTag(Long id, UpdateTagDTO dto);
    void deleteTag(Long id);
    TagDTO getById(Long id);
    List<TagDTO> getAll();
    List<TagDTO> getTagsByPage(int pageNumber, int pageSize);
}
