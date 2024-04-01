package com.constructionnote.constructionnote.dto.construction;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    private Date timeBegin;
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
