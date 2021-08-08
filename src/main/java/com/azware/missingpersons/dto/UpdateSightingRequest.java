package com.azware.missingpersons.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class UpdateSightingRequest {

    private long id;

    private long reportId;

    private Instant sightingTime;

    private String sightingLocation;

    private String imageUri;

    private String additionalInfo;
}
