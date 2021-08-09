package com.azware.missingpersons.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateSightingRequest {

    @NotNull
    private long reportId;

    @NotNull
    private Instant sightingTime;

    @NotNull
    private String sightingLocation;

    private String imageUri;

    private String additionalInfo;
}
