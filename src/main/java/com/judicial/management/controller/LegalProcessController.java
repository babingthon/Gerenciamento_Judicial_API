package com.judicial.management.controller;

import com.judicial.management.dto.LegalProcessDto;
import com.judicial.management.model.LegalProcess;
import com.judicial.management.model.enums.LegalProcessEnum;
import com.judicial.management.service.LegalProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/process")
@RequiredArgsConstructor
@Tag(name = "Process", description = "Endpoints for managing legal processes.")
public class LegalProcessController {

    private final LegalProcessService legalProcessService;

    @PostMapping
    @Operation(summary = "Creates a new legal process")
    public ResponseEntity<LegalProcess> createLegalProcess(@Valid @RequestBody LegalProcessDto legalProcessDto) {
        LegalProcess newLegalProcess = legalProcessService.save(legalProcessDto);
        return new ResponseEntity<>(newLegalProcess, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "List processes with optional filters by status and district.")
    public ResponseEntity<List<LegalProcess>> listLegalProcess(
            @RequestParam(required = false) LegalProcessEnum status,
            @RequestParam(required = false) String district) {
        List<LegalProcess> processes = legalProcessService.listProcesses(status, district);
        return ResponseEntity.ok(processes);
    }
}
