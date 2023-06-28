package com.auction.flab.application.service;

import com.auction.flab.application.exception.ErrorCode;
import com.auction.flab.application.exception.SystemException;
import com.auction.flab.application.mapper.ProjectMapper;
import com.auction.flab.application.vo.ProjectVo;
import com.auction.flab.application.web.dto.ProjectRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectMapper projectMapper;

    @Transactional
    public void addProject(ProjectRequestDto projectRequestDto) {
        ProjectVo projectVo = ProjectVo.toProjectVo(projectRequestDto);
        int result = projectMapper.insertProject(projectVo);
        if (result < 1) {
            throw new SystemException(ErrorCode.EXCEPTION_ON_INPUT_PROJECT);
        }
    }

}
