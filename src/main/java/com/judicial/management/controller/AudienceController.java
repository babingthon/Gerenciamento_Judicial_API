package com.judicial.management.controller;

import com.judicial.management.dto.AudienceDto;
import com.judicial.management.dto.AudienceResponseDto;
import com.judicial.management.dto.AudienceScheduleDto;
import com.judicial.management.model.Audience;
import com.judicial.management.service.AudienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/audience")
@RequiredArgsConstructor
@Tag(name = "Audience", description = "Endpoints for scheduling and consulting audiences.")
public class AudienceController {

    private final AudienceService audienceService;

    @PostMapping
    @Operation(summary = "Schedule a new audience for a case.")
    public ResponseEntity<AudienceResponseDto> scheduleAudience(@Valid @RequestBody AudienceDto audienceDto) {
        AudienceResponseDto response = audienceService.scheduleAudience(audienceDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/schedule")
    @Operation(summary = "Check the audience schedule for a district on a given day.")
    public ResponseEntity<List<AudienceScheduleDto>> listAppointments(
            @RequestParam String district,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate day){

        List<AudienceScheduleDto> appointments = audienceService.listAudienteByDistrictAndDay(district, day);
        return ResponseEntity.ok(appointments);
    }
}
