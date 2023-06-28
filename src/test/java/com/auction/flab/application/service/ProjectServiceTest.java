package com.auction.flab.application.service;

import com.auction.flab.application.exception.ErrorCode;
import com.auction.flab.application.exception.SystemException;
import com.auction.flab.application.mapper.ProjectMapper;
import com.auction.flab.application.vo.ProjectVo;
import com.auction.flab.application.web.dto.ProjectRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    ProjectMapper projectMapper;
    @InjectMocks
    ProjectService projectService;
    ProjectRequestDto projectRequestDto;

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

    @DisplayName("insertProject() fail")
    @Test
    void addProject_fail_with_exception() {
        // given
        given(projectMapper.insertProject(any(ProjectVo.class))).willReturn(0);

        // when
        SystemException systemException = assertThrows(SystemException.class, () -> projectService.addProject(projectRequestDto));

        // then
        assertEquals(ErrorCode.EXCEPTION_ON_INPUT_PROJECT, systemException.getErrorCode());
        then(projectMapper).should(times(1)).insertProject(any(ProjectVo.class));
    }

    @DisplayName("insertProject() success")
    @Test
    void addProject_success_without_exception() {
        // given
        given(projectMapper.insertProject(any(ProjectVo.class))).willReturn(1);

        // when
        projectService.addProject(projectRequestDto);

        // then
        then(projectMapper).should(times(1)).insertProject(any(ProjectVo.class));
    }

}