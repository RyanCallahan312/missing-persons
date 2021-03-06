package com.azware.missingpersons.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import lombok.Data;

import java.time.Instant;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "sighting")
public class SightingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sighting_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", referencedColumnName = "report_id",  nullable = false)
    private ReportEntity report;

    @NotNull
    private Instant sightingTime;

    @NotNull
    private String sightingLocation;

    @NotNull
    private String imageUri;

    @NotNull
    private String additionalInfo;
}
