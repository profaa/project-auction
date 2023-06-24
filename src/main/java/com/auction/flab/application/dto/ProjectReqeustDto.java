package com.auction.flab.application.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProjectReqeustDto {

    @NotNull(message = "제안자는 필수 입력 항목 입니다.")
    private Long proposerId;

    @NotBlank(message = "프로젝트명은 필수 입력 항목 입니다.")
    private String name;

    @NotNull(message = "금액은 필수 입력 항목 입니다.")
    @Range(min = 1, max = 100_000, message = "예상금액은 1만원 이상, 100,000만원 이하 입니다.")
    private Integer amount;

    @NotNull(message = "예상기간은 필수 입력 항목 입니다.")
    @Range(min = 1, max = 1_000, message = "예상기간은 1일 이상, 1,000일 이하 입니다.")
    private Integer period;

    @Future(message = "모집마감일은 현재보다 이전 시간으로 지정할 수 없습니다.")
    @NotNull(message = "모집마감일은 필수 입력 항목 입니다.")
    @DateTimeFormat
    private LocalDateTime deadline;

    @Future(message = "모집마감일은 현재보다 이전 시간으로 지정할 수 없습니다.")
    @NotNull(message = "예상시작일은 필수 입력 항목 입니다.")
    @DateTimeFormat
    private LocalDateTime startDate;

    @NotBlank(message = "업무내용은 필수 입력 항목 입니다.")
    private String content;

}
