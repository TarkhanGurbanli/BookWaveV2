package com.tarkhan.backend.service.impl;

import com.tarkhan.backend.entity.Category;
import com.tarkhan.backend.entity.Tag;
import com.tarkhan.backend.exception.BookWaveApiException;
import com.tarkhan.backend.exception.ResourceNotFoundException;
import com.tarkhan.backend.model.tag.CreateTagDTO;
import com.tarkhan.backend.model.tag.TagDTO;
import com.tarkhan.backend.model.tag.UpdateTagDTO;
import com.tarkhan.backend.repository.TagRepository;
import com.tarkhan.backend.service.TagService;
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
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createTag(CreateTagDTO dto) {
        CreateTagDTO createTagDTO = modelMapper.map(dto, CreateTagDTO.class);

        if(tagRepository.existsByName(createTagDTO.getName())) {
            throw new BookWaveApiException("Tag already exists");
        }

        tagRepository.save(modelMapper.map(createTagDTO, Tag.class));
    }

    @Override
    public void updateTag(Long id, UpdateTagDTO dto) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tag", "ID", id));

        modelMapper.map(dto, tag);
        tagRepository.save(modelMapper.map(tag, Tag.class));
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tag", "ID", id));
        tagRepository.delete(tag);
    }

    @Override
    public TagDTO getById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tag", "ID", id));

        return modelMapper.map(tag, TagDTO.class);
    }

    @Override
    public List<TagDTO> getAll() {
       List<Tag> tags = tagRepository.findAll();
       return tags.stream()
               .map(tag -> modelMapper.map(tag, TagDTO.class))
               .collect(Collectors.toList());
    }

    @Override
    public List<TagDTO> getTagsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        List<Tag> tags = tagRepository.findAll(pageable).getContent();
        return tags.stream()
                .map(tag -> modelMapper.map(tag, TagDTO.class))
                .collect(Collectors.toList());
    }
}
