package com.auction.flab.application.web.dto;

import com.auction.flab.application.validator.DateTimeOrder;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@DateTimeOrder(previousDateTime = "deadline", laterDateTime = "startDate", message = "예상시작일은 모집마감일 이후로 지정할 수 있습니다.")
@Builder
@Getter
@Setter
@ToString
public class ProjectRequestDto {

    @NotNull(message = "제안자는 필수 입력 항목 입니다.")
    private Long proposerId;

    @NotBlank(message = "프로젝트명은 필수 입력 항목 입니다.")
    private String name;

    @Range(min = 1, max = 100_000, message = "예상금액은 1만원 이상, 100,000만원 이하 입니다.")
    @NotNull(message = "금액은 필수 입력 항목 입니다.")
    private Integer amount;

    @Range(min = 1, max = 1_000, message = "예상기간은 1일 이상, 1,000일 이하 입니다.")
    @NotNull(message = "예상기간은 필수 입력 항목 입니다.")
    private Integer period;

    @Future(message = "모집마감일은 현재보다 이전 시간으로 지정할 수 없습니다.")
    @DateTimeFormat
    @NotNull(message = "모집마감일은 필수 입력 항목 입니다.")
    private LocalDateTime deadline;

    @Future(message = "모집마감일은 현재보다 이전 시간으로 지정할 수 없습니다.")
    @DateTimeFormat
    @NotNull(message = "예상시작일은 필수 입력 항목 입니다.")
    private LocalDateTime startDate;

    @Length(max = 2000)
    @NotBlank(message = "업무내용은 필수 입력 항목 입니다.")
    private String content;

}
