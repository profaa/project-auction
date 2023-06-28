package com.auction.flab.application.web.controller;

import com.auction.flab.application.service.ProjectService;
import com.auction.flab.application.web.dto.ProjectRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/projects")
    public ResponseEntity<Void> addProject(@Valid @RequestBody ProjectRequestDto projectDto) {
        projectService.addProject(projectDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
