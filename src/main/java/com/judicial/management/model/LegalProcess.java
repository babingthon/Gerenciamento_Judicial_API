package com.judicial.management.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.judicial.management.model.enums.LegalProcessEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "legal_process")
public class LegalProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^\\d{7}-\\d{2}\\.\\d{4}\\.\\d\\.\\d{2}\\.\\d{4}$", message = "Invalid legalProcess number format.")
    @Column(name = "case_number", unique = true, nullable = false)
    private String caseNumber;

    @Column(name = "court")
    @NotBlank(message = "the 'court' field cannot be empty.")
    private String court;

    @Column(name = "district")
    @NotBlank(message = "the 'district' field cannot be empty.")
    private String district;

    @Column(name = "subject")
    @NotBlank(message = "the 'subject' field cannot be empty.")
    private String subject;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "the 'status' field is required.")
    private LegalProcessEnum status;

    @OneToMany(mappedBy = "legalProcess", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Audience> audiences = new ArrayList<>();
}
