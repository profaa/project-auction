package com.auction.flab.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProjectResponseDto {

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
