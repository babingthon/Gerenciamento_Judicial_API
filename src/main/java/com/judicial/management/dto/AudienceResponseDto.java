package com.judicial.management.dto;

import com.judicial.management.model.enums.AudienceEnum;

import java.time.LocalDateTime;

public record AudienceResponseDto(
        Long id,
        LocalDateTime dateTime,
        String location,
        AudienceEnum audienceType,
        String legalProcessCaseNumber
) {}
