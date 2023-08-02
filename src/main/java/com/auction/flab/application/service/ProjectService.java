package com.auction.flab.application.service;

import com.auction.flab.application.exception.ErrorCode;
import com.auction.flab.application.exception.InternalException;
import com.auction.flab.application.mapper.ProjectMapper;
import com.auction.flab.application.mapper.ProjectStatus;
import com.auction.flab.application.vo.ProjectVo;
import com.auction.flab.application.web.dto.ProjectRequestDto;
import com.auction.flab.application.web.dto.ProjectResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectResponseDto addProject(ProjectRequestDto projectRequestDto) {
        ProjectVo projectVo = ProjectVo.from(projectRequestDto);
        projectMapper.insertProject(projectVo);
        if (projectVo.getId() == null) {
            throw new InternalException(ErrorCode.EXCEPTION_ON_INPUT_PROJECT);
        }
        return ProjectResponseDto.from(projectVo.getId());
    }

    @Transactional
    public ProjectResponseDto updateProject(Long id, ProjectRequestDto projectRequestDto) {
        ProjectVo projectVo = ProjectVo.from(projectRequestDto);
        ProjectVo result = projectMapper.selectProject(id);
        if (result == null || result.getStatus().equals(ProjectStatus.CONFIRMATION)) {
            throw new InternalException(ErrorCode.EXCEPTION_ON_UPDATE_PROJECT);
        }
        projectVo.setId(id);
        projectMapper.updateProject(projectVo);
        return ProjectResponseDto.from(id);
    }

    @Transactional
    public void deleteProject(Long id) {
        ProjectVo result = projectMapper.selectProject(id);
        if (result == null || !result.getStatus().equals(ProjectStatus.PROPOSAL)) {
            throw new InternalException(ErrorCode.EXCEPTION_ON_DELETE_PROJECT);
        }
        projectMapper.deleteProject(id);
    }

}
