package com.auction.flab.application.service.impl;

import com.auction.flab.application.dto.ProjectDto;
import com.auction.flab.application.mapper.AuctionMapper;
import com.auction.flab.application.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionMapper auctionMapper;

    @Override
    public List<ProjectDto> getProjects() {
        return auctionMapper.getProjects();
    }

    @Transactional
    public void addProject(ProjectDto projectDto) {
        auctionMapper.addProject(projectDto);
    }

}
