package com.azware.missingpersons.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

import java.time.Instant;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity(name="sighting")
public class SightingEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sighting_id")
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="report_id")
    private ReportEntity report;

    @NotNull
    private Instant sightingTime;

    private String sightingLocation;

    private String sightingImageUri;

    private String additionalInfo;
}
