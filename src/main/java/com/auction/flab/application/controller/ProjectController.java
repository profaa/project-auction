package com.auction.flab.application.controller;

import com.auction.flab.application.dto.ProjectReqeustDto;
import com.auction.flab.application.service.ProjectService;
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

    private final ProjectService auctionService;

    @PostMapping("/projects")
    public ResponseEntity<?> addProject(@Valid @RequestBody ProjectReqeustDto projectDto) {
        validate(projectDto);
        auctionService.addProject(projectDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void validate(ProjectReqeustDto projectDto) {
        if (!projectDto.getStartDate().isAfter(projectDto.getDeadline())) {
            throw new IllegalArgumentException("예상시작일은 모집마감일 이후로 지정할 수 있습니다.");
        }
    }

}
