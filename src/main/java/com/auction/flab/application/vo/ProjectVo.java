package com.auction.flab.application.vo;

import com.auction.flab.application.ProjectStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class ProjectVo {

    private Long id;
    private Long proposerId;
    private String name;
    private Integer amount;
    private Integer period;
    private LocalDateTime deadline;
    private LocalDateTime startDate;
    private String content;
    private String status;

}
