package com.azware.missingpersons.model;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity(name = "report")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private long id;

    @NotNull
    private String missingPersonName;

    @NotNull
    private String reporterName;

    @NotNull
    private String description;

    @NotNull
    private Instant reportTime;

    private Instant lastSeenTime;

    private String lastKnownLocation;

    private String additionalInfo;

    private String imageUri;

    @NotNull
    private boolean isFound;

    @OneToMany(mappedBy="report", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<SightingEntity> sightings;

}
