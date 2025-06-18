package com.judicial.management.service;

import com.judicial.management.dto.AudienceDto;
import com.judicial.management.exception.BusinessRuleException;
import com.judicial.management.model.LegalProcess;
import com.judicial.management.model.enums.AudienceEnum;
import com.judicial.management.model.enums.LegalProcessEnum;
import com.judicial.management.repository.AudienceRepository;
import com.judicial.management.repository.LegalProcessRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AudienceServiceTest {

    @Mock
    private LegalProcessRepository legalProcessRepository;
    @Mock
    private AudienceRepository audienceRepository;
    @InjectMocks
    private AudienceService audienceService;

    @Test
    @DisplayName("Should throw exception when trying to schedule audience for case with ARCHIVED status.")
    void Teste_1(){
        long processId = 1L;
        var requestDto = new AudienceDto(
                processId,
                LocalDateTime.now().plusDays(1),
                AudienceEnum.CONCILIATION,
                "Sala 1");
        var processArchived = new LegalProcess();
        processArchived.setId(processId);
        processArchived.setStatus(LegalProcessEnum.ARCHIVED);

        when(legalProcessRepository.findById(processId)).thenReturn(Optional.of(processArchived));

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> audienceService.scheduleAudience(requestDto)
        );

        assertEquals("It is not possible to schedule a hearing for a case with status ARCHIVED", exception.getMessage());
        verify(audienceRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when trying to schedule audience on a Saturday")
    void Test_2(){

        long processId = 2L;

        LocalDateTime nextSaturday = LocalDateTime.now();
        while (nextSaturday.getDayOfWeek() != DayOfWeek.SATURDAY){
            nextSaturday = nextSaturday.plusDays(1);
        }

        var requestDto = new AudienceDto(
                processId,
                nextSaturday,
                AudienceEnum.INSTRUCTION,
                "Sala 2"
        );

        var activeProcess = new LegalProcess();
        activeProcess.setId(processId);
        activeProcess.setStatus(LegalProcessEnum.ACTIVE);

        when(legalProcessRepository.findById(processId)).thenReturn(Optional.of(activeProcess));

        var exception = assertThrows(
                BusinessRuleException.class,
                () -> audienceService.scheduleAudience(requestDto)
        );

        assertEquals("Audiences can only be scheduled on weekdays (Monday to Friday).", exception.getMessage());
        verify(audienceRepository, never()).save(any());
    }

}
