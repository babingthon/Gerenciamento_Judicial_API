package com.judicial.management.dto;

import com.judicial.management.model.enums.LegalProcessEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LegalProcessDto(
        @NotBlank @Pattern(regexp = "^\\d{7}-\\d{2}\\.\\d{4}\\.\\d\\.\\d{2}\\.\\d{4}$", message = "Invalid legalProcess number format.")
        String caseNumber,
        @NotBlank String court,
        @NotBlank String district,
        @NotBlank String subject,
        @NotNull LegalProcessEnum status) {
}
