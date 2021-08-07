package com.azware.missingpersons.model;

import javax.persistence.Entity;

import lombok.Data;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class SightingEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="report_id")
    private ReportEntity report;

    @NotNull
    private Instant sightingTime;

    private String sightingLocation;

    private String sightingImageURI;

    private String additionalInfo;
}
