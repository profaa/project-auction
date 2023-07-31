package com.auction.flab.application.web.controller;

import com.auction.flab.application.service.ProjectService;
import com.auction.flab.application.web.dto.ProjectRequestDto;
import com.auction.flab.application.web.dto.ProjectResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/projects")
    public ResponseEntity<ProjectResponseDto> addProject(@Valid @RequestBody ProjectRequestDto projectDto) {
        ProjectResponseDto projectResponseDto = projectService.addProject(projectDto);
        return new ResponseEntity<>(projectResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequestDto projectDto) {
        ProjectResponseDto projectResponseDto = projectService.updateProject(id, projectDto);
        return new ResponseEntity<>(projectResponseDto, HttpStatus.CREATED);
    }

}
