package com.auction.flab.application.web.controller;

import com.auction.flab.application.service.ProjectService;
import com.auction.flab.application.web.dto.ProjectRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMybatis
@WebMvcTest(controllers = ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    ProjectService projectService;

    private ProjectRequestDto projectRequestDto;

    @BeforeEach
    void setUp() {
        projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 07, 03, 00, 00, 00))
                .startDate(LocalDateTime.of(2023, 07, 11, 00, 00, 00))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();
    }

    void testBadRequest() throws Exception {
        willDoNothing().given(projectService).addProject(any());
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Null proposerId input")
    @Test
    void invalid_proposerId_null() throws Exception {
        projectRequestDto.setProposerId(null);
        testBadRequest();
    }

    @DisplayName("Blank name input")
    @Test
    void invalid_name_blank() throws Exception {
        projectRequestDto.setName(null);
        testBadRequest();
        projectRequestDto.setName("");
        testBadRequest();
        projectRequestDto.setName("     ");
        testBadRequest();
    }

    @DisplayName("Null amount input")
    @Test
    void invalid_amount_null() throws Exception {
        projectRequestDto.setAmount(null);
        testBadRequest();
    }

    @DisplayName("Range amount input")
    @Test
    void invalid_amount_range() throws Exception {
        projectRequestDto.setAmount(0);
        testBadRequest();
        projectRequestDto.setAmount(100_001);
        testBadRequest();
    }

    @DisplayName("Null period input")
    @Test
    void invalid_period_null() throws Exception {
        projectRequestDto.setPeriod(null);
        testBadRequest();
    }

    @DisplayName("Range period input")
    @Test
    void invalid_period_range() throws Exception {
        projectRequestDto.setPeriod(0);
        testBadRequest();
        projectRequestDto.setPeriod(1_001);
        testBadRequest();
    }

    @DisplayName("Null deadline input")
    @Test
    void invalid_deadline_null() throws Exception {
        projectRequestDto.setDeadline(null);
        testBadRequest();
    }

    @DisplayName("Future deadline input")
    @Test
    void invalid_deadline_future() throws Exception {
        projectRequestDto.setDeadline(LocalDateTime.now().minusSeconds(1L));
        testBadRequest();
    }

    @DisplayName("Null startDate input")
    @Test
    void invalid_startDate_null() throws Exception {
        projectRequestDto.setStartDate(null);
        testBadRequest();
    }

    @DisplayName("Future startDate input")
    @Test
    void invalid_startDate_future() throws Exception {
        projectRequestDto.setStartDate(LocalDateTime.now().minusSeconds(1L));
        testBadRequest();
    }

    @DisplayName("Blank content input")
    @Test
    void invalid_content_blank() throws Exception {
        projectRequestDto.setContent(null);
        testBadRequest();
        projectRequestDto.setContent("");
        testBadRequest();
        projectRequestDto.setContent("     ");
        testBadRequest();
    }

    @DisplayName("Max content input")
    @Test
    void invalid_content_max() throws Exception {
        String[] contents = new String[2001];
        Arrays.fill(contents, "a");
        projectRequestDto.setContent(String.join("", contents));
        testBadRequest();
    }

    @DisplayName("DateTime order input")
    @Test
    void invalid_datetime_order() throws Exception {
        projectRequestDto.setDeadline(projectRequestDto.getStartDate().plusDays(1L));
        testBadRequest();
    }

}