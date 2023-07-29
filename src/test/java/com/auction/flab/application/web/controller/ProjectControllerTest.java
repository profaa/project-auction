package com.auction.flab.application.web.controller;

import com.auction.flab.application.service.ProjectService;
import com.auction.flab.application.web.dto.ProjectRequestDto;
import com.auction.flab.application.web.dto.ProjectResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ProjectController.class)
class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProjectService projectService;

    @Test
    void valid_request() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();
        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }
    @Test
    void invalid_proposerId_with_null() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(null)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();
        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_name_with_null() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name(null)
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_name_with_empty() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_name_with_blank() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("     ")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_amount_with_null() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(null)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_amount_below_the_minimum() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(0)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_amount_above_the_maximum() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(100_001)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_period_with_null() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(null)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_period_below_the_minimum() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(0)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_period_above_the_maximum() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(1_001)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_deadline_with_null() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(null)
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_deadline_with_past_date() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.now().minusSeconds(1L))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_startDate_with_null() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(null)
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_startDate_with_past_date() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.now().minusSeconds(1L))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_content_with_null() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content(null)
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_content_with_empty() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("")
                .build();
        projectRequestDto.setContent("");

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_content_with_blank() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("     ")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_content_beyond_maximum_length() throws Exception {
        String[] contents = new String[2001];
        Arrays.fill(contents, "a");
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content(String.join("", contents))
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalid_request_because_startDate_is_less_than_deadline() throws Exception {
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 8, 11, 0, 0, 0).plusDays(1L))
                .startDate(LocalDateTime.of(2023, 8, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();

        given(projectService.addProject(eq(projectRequestDto))).willReturn(ProjectResponseDto.from(1L));
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}