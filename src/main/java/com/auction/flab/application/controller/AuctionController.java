package com.auction.flab.application.controller;

import com.auction.flab.application.dto.ProjectDto;
import com.auction.flab.application.exception.ExceptionDto;
import com.auction.flab.application.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping("/projects")
    public List<ProjectDto> projects() {
        return auctionService.getProjects();
    }

    @PostMapping("/projects")
    public ResponseEntity addProject(@Validated @RequestBody ProjectDto projectDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(message));
        }
        validate(projectDto);
        projectDto.setProposerId(1);    // 제안자ID => 1
        auctionService.addProject(projectDto);
        return new ResponseEntity(createResponseHeader(projectDto), HttpStatus.CREATED);
    }

    private void validate(ProjectDto projectDto) {
        if (!projectDto.getStartDate().isAfter(projectDto.getDeadline())) {
            throw new IllegalArgumentException("예상시작일은 모집마감일 이후로 지정할 수 있습니다.");
        }
    }

    private MultiValueMap createResponseHeader(ProjectDto projectDto) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Location", "/projects/" + projectDto.getId());
        return headers;
    }

}
