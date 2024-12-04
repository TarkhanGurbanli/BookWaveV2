package com.tarkhan.backend.controller;

import com.tarkhan.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
}
