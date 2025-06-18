package com.judicial.management.service;

import com.judicial.management.dto.LegalProcessDto;
import com.judicial.management.exception.BusinessRuleException;
import com.judicial.management.exception.ResourceNotFoundException;
import com.judicial.management.model.LegalProcess;
import com.judicial.management.model.enums.LegalProcessEnum;
import com.judicial.management.repository.LegalProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LegalProcessService {
    private final LegalProcessRepository legalProcessRepository;

    @Transactional
    public LegalProcess save(LegalProcessDto legalProcessDto) {
        legalProcessRepository.findByCaseNumber(legalProcessDto.caseNumber()).ifPresent(legalProcess -> {
            throw new BusinessRuleException("There is already a registered process with the number: "  + legalProcessDto.caseNumber());
        });

        LegalProcess newLegalProcess = new LegalProcess();
        newLegalProcess.setCaseNumber(legalProcessDto.caseNumber());
        newLegalProcess.setCourt(legalProcessDto.court());
        newLegalProcess.setDistrict(legalProcessDto.district());
        newLegalProcess.setSubject(legalProcessDto.subject());
        newLegalProcess.setStatus(legalProcessDto.status());

        return legalProcessRepository.save(newLegalProcess);
    }

    @Transactional(readOnly = true)
    public List<LegalProcess> listProcesses(LegalProcessEnum status, String district){
      if (status != null && district != null && !district.isBlank()) {
          return legalProcessRepository.findByStatusAndDistrict(status, district);
      }
      if (status != null){
          return legalProcessRepository.findByStatus(status);
      }
      if (district != null && !district.isBlank()) {
          return legalProcessRepository.findByDistrict(district);
      }

      return legalProcessRepository.findAll();
    }

    @Transactional(readOnly = true)
    public LegalProcess findById(Long id) {
        return legalProcessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Process with ID" + id + "not found."));
    }
}

