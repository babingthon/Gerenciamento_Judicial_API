package com.judicial.management.service;

import com.judicial.management.dto.AudienceDto;
import com.judicial.management.dto.AudienceResponseDto;
import com.judicial.management.dto.AudienceScheduleDto;
import com.judicial.management.exception.BusinessRuleException;
import com.judicial.management.exception.ResourceNotFoundException;
import com.judicial.management.model.Audience;
import com.judicial.management.model.LegalProcess;
import com.judicial.management.model.enums.LegalProcessEnum;
import com.judicial.management.repository.AudienceRepository;
import com.judicial.management.repository.LegalProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AudienceService {

    private final AudienceRepository audienceRepository;
    private final LegalProcessRepository legalProcessRepository;

    public AudienceResponseDto scheduleAudience(AudienceDto audienceDto) {
        LegalProcess process = legalProcessRepository.findById(audienceDto.processId())
                .orElseThrow(() -> new ResourceNotFoundException("Process with ID " + audienceDto.processId() + " not found for scheduling."));

        if (!Objects.equals(process.getStatus(), LegalProcessEnum.ACTIVE)){
            throw new BusinessRuleException("It is not possible to schedule a hearing for a case with status " +  process.getStatus());
        }

        DayOfWeek dayOfTheWeek = audienceDto.dateTime().getDayOfWeek();
        if (dayOfTheWeek == DayOfWeek.SATURDAY || dayOfTheWeek == DayOfWeek.SUNDAY){
            throw new BusinessRuleException("Audiences can only be scheduled on weekdays (Monday to Friday).");
        }

        audienceRepository.findAudience(process.getCourt(), audienceDto.location(), audienceDto.dateTime())
                .ifPresent(audience -> {
                    throw new BusinessRuleException("There is already a audience scheduled for this court, location and time.");
                });

        Audience newAudience = new Audience();
        newAudience.setLegalProcess(process);
        newAudience.setDateTime(audienceDto.dateTime());
        newAudience.setAudienceType(audienceDto.typeAudience());
        newAudience.setLocation(audienceDto.location());

        Audience saved = audienceRepository.save(newAudience);

        return new AudienceResponseDto(
                saved.getId(),
                saved.getDateTime(),
                saved.getLocation(),
                saved.getAudienceType(),
                saved.getLegalProcess().getCaseNumber()
        );
    }

    @Transactional(readOnly = true)
    public List<AudienceScheduleDto> listAudienteByDistrictAndDay(String district, LocalDate day){
        return audienceRepository.findByDistrictAndDay(district, day).stream()
                .map(audience -> new AudienceScheduleDto(
                        audience.getId(),
                        audience.getDateTime(),
                        audience.getAudienceType(),
                        audience.getLocation(),
                        audience.getLegalProcess().getCaseNumber(),
                        audience.getLegalProcess().getCourt()
                ))
                .toList();
    }
}
