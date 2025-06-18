package com.judicial.management.dto;

import com.judicial.management.model.enums.AudienceEnum;

import java.time.LocalDateTime;

public record AudienceScheduleDto(
        Long audienceId,
        LocalDateTime dateTime,
        AudienceEnum typeAudience,
        String location,
        String caseNumber,
        String court) {
}
