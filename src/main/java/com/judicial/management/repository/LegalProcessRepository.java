package com.judicial.management.repository;

import com.judicial.management.model.LegalProcess;
import com.judicial.management.model.enums.LegalProcessEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LegalProcessRepository extends JpaRepository<LegalProcess, Long> {
    Optional<LegalProcess> findByCaseNumber(String caseNumber);
    List<LegalProcess> findByStatus(LegalProcessEnum status);
    List<LegalProcess> findByDistrict(String district);
    List<LegalProcess> findByStatusAndDistrict(LegalProcessEnum status, String district);
}
