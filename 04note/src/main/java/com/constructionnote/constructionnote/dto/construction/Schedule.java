package com.constructionnote.constructionnote.dto.construction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Schema(title = "공사 스케줄 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    @Schema(description = "공사 시작일", example = "2024-03-11")
    private Date timeBegin;
    @Schema(description = "공사 종료일", example = "2024-03-17")
    private Date timeEnd;

    @Builder
    public Schedule(Date timeBegin, Date timeEnd) {
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
    }

    public static Schedule createSchedule(Date timeBegin, Date timeEnd) {
        return Schedule.builder()
                .timeBegin(timeBegin)
                .timeEnd(timeEnd)
                .build();
    }
}
