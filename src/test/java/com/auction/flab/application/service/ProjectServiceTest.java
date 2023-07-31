package com.auction.flab.application.service;

import com.auction.flab.application.exception.ErrorCode;
import com.auction.flab.application.exception.InternalException;
import com.auction.flab.application.mapper.ProjectMapper;
import com.auction.flab.application.mapper.ProjectStatus;
import com.auction.flab.application.vo.ProjectVo;
import com.auction.flab.application.web.dto.ProjectRequestDto;
import com.auction.flab.application.web.dto.ProjectResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    ProjectMapper projectMapper;
    @InjectMocks
    ProjectService projectService;

    @Test
    void add_project_success() {
        // given
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 7, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 7, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();
        ProjectVo projectVo = ProjectVo.from(projectRequestDto);
        given(projectMapper.insertProject(eq(projectVo))).will(invocation -> {
            projectVo.setId(1L);
            ProjectVo arg = invocation.getArgument(0);
            arg.setId(1L);
            return 1;
        });

        // when
        ProjectResponseDto projectResponseDto = projectService.addProject(projectRequestDto);

        // then
        assertEquals(1L, projectResponseDto.getId());
        then(projectMapper).should(times(1)).insertProject(eq(projectVo));
    }

    @Test
    void add_project_failed_due_to_db_exception() {
        // given
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 7, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 7, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();
        given(projectMapper.insertProject(eq(ProjectVo.from(projectRequestDto)))).willReturn(1);

        // when
        InternalException internalException = assertThrows(InternalException.class, () -> projectService.addProject(projectRequestDto));

        // then
        assertEquals(ErrorCode.EXCEPTION_ON_INPUT_PROJECT, internalException.getErrorCode());
        then(projectMapper).should(times(1)).insertProject(eq(ProjectVo.from(projectRequestDto)));
    }

    @Test
    void update_project_success() {
        // given
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트(수정)")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 7, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 7, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();
        ProjectVo projectVo = ProjectVo.from(projectRequestDto);
        given(projectMapper.insertProject(eq(projectVo))).will(invocation -> {
            projectVo.setId(1L);
            ProjectVo arg = invocation.getArgument(0);
            arg.setId(1L);
            return 1;
        });

        // when
        ProjectResponseDto projectResponseDto = projectService.addProject(projectRequestDto);

        // then
        assertEquals(1L, projectResponseDto.getId());
        then(projectMapper).should(times(1)).insertProject(eq(projectVo));
    }

    @DisplayName("프로젝트 수정 실패 - 정보를 업데이트할 프로젝트가 미존재")
    @Test
    void update_project_failed_due_to_no_project() {
        // given
        Long projectId = 1L;
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 7, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 7, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();
        given(projectMapper.selectProject(eq(projectId))).willReturn(null);

        // when
        InternalException internalException = assertThrows(InternalException.class, () -> projectService.updateProject(projectId, projectRequestDto));

        // then
        assertEquals(ErrorCode.EXCEPTION_ON_UPDATE_PROJECT, internalException.getErrorCode());
        then(projectMapper).should(times(1)).selectProject(projectId);
    }

    @DisplayName("프로젝트 수정 실패 - 프로젝트가 완료 상태인 경우 수정 불가")
    @Test
    void update_project_failed_due_to_invalid_status() {
        // given
        Long projectId = 1L;
        ProjectRequestDto projectRequestDto = ProjectRequestDto.builder()
                .proposerId(1L)
                .name("날씨 정보 오픈 API 프로젝트")
                .amount(3_000)
                .period(100)
                .deadline(LocalDateTime.of(2023, 7, 3, 0, 0, 0))
                .startDate(LocalDateTime.of(2023, 7, 11, 0, 0, 0))
                .content("날짜 정보를 제공하는 API를 작성하는 프로젝트 입니다.")
                .build();
        ProjectVo projectVo = ProjectVo.from(projectRequestDto);
        projectVo.setStatus(ProjectStatus.CONFIRMATION);
        given(projectMapper.selectProject(eq(projectId))).willReturn(projectVo);

        // when
        InternalException internalException = assertThrows(InternalException.class, () -> projectService.updateProject(projectId, projectRequestDto));

        // then
        assertEquals(ErrorCode.EXCEPTION_ON_UPDATE_PROJECT, internalException.getErrorCode());
        then(projectMapper).should(times(1)).selectProject(projectId);
    }
    
}