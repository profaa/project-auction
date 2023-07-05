package com.auction.flab.application.service;

import com.auction.flab.application.exception.ErrorCode;
import com.auction.flab.application.exception.InternalException;
import com.auction.flab.application.mapper.ProjectMapper;
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

}
