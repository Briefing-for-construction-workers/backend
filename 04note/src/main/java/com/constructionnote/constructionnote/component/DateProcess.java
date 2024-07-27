package com.constructionnote.constructionnote.component;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class DateProcess {

    public String convertToRelativeTime(Timestamp createdAt) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(createdAt.toLocalDateTime(), currentTime);

        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();

        String relativeTime;
        if (days > 0) {
            relativeTime = days + "일 전";
        } else if (hours > 0) {
            relativeTime = hours + "시간 전";
        } else if (minutes > 0) {
            relativeTime = minutes + "분 전";
        } else {
            relativeTime = "방금";
        }

        return relativeTime;
    }
}
