package com.auction.flab.application.dao;

import com.auction.flab.application.mapper.ProjectMapper;
import com.auction.flab.application.vo.ProjectVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProjectDao {

    private final ProjectMapper auctionMapper;

    public int addProject(ProjectVo projectVo) {
        return auctionMapper.addProject(projectVo);
    }

}
