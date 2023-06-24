package com.auction.flab.application.service;

import com.auction.flab.application.ProjectStatus;
import com.auction.flab.application.dao.ProjectDao;
import com.auction.flab.application.dto.ProjectReqeustDto;
import com.auction.flab.application.vo.ProjectVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectDao projectDao;

    @Transactional
    public void addProject(ProjectReqeustDto projectReqeustDto) {
        ProjectVo projectVo = ProjectVo.builder()
                .proposerId(projectReqeustDto.getProposerId())
                .name(projectReqeustDto.getName())
                .amount(projectReqeustDto.getAmount())
                .period(projectReqeustDto.getPeriod())
                .deadline(projectReqeustDto.getDeadline())
                .startDate(projectReqeustDto.getStartDate())
                .content(projectReqeustDto.getContent())
                .status(ProjectStatus.PROPOSAL.getStatus())
                .build();
        projectDao.addProject(projectVo);
    }

}
