package com.judicial.management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.judicial.management.model.enums.AudienceEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "audience")
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date and time are required.")
    private LocalDateTime dateTime;

    @NotBlank(message = "The location cannot be empty.")
    private String location;

    @NotNull(message = "The audience type cannot be null.")
    @Enumerated(EnumType.STRING)
    private AudienceEnum audienceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="process_id", nullable = false)
    @JsonBackReference
    private LegalProcess legalProcess;
}
