package com.judicial.management.dto;

import com.judicial.management.model.enums.AudienceEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AudienceDto(
        @NotNull(message = "Process ID is required.")
        Long processId,
        @NotNull(message = "Date and time are required.")
        @Future(message = "The audience date must be in the future.")
        LocalDateTime dateTime,
        @NotNull AudienceEnum typeAudience,
        @NotBlank String location) {
}
