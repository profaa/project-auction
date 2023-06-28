package com.auction.flab.application.vo;

import com.auction.flab.application.mapper.ProjectStatus;
import com.auction.flab.application.web.dto.ProjectRequestDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVo {

    private Long id;
    private Long proposerId;
    private String name;
    private Integer amount;
    private Integer period;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
    private String content;
    private ProjectStatus status;

    public static ProjectVo toProjectVo(ProjectRequestDto projectRequestDto) {
        return new ProjectVo().builder()
                .proposerId(projectRequestDto.getProposerId())
                .name(projectRequestDto.getName())
                .amount(projectRequestDto.getAmount())
                .period(projectRequestDto.getPeriod())
                .deadline(projectRequestDto.getDeadline())
                .startDate(projectRequestDto.getStartDate())
                .content(projectRequestDto.getContent())
                .status(ProjectStatus.PROPOSAL)
                .build();
    }

}
