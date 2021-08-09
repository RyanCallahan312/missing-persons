package com.azware.missingpersons.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateReportRequest {

    @NotNull
    private String missingPersonName;

    @NotNull
    private String reporterName;

    @NotNull
    private String description;

    @NotNull
    private Instant lastSeenTime;

    @NotNull
    private String lastKnownLocation;

    @NotNull
    private String additionalInfo;

    @NotNull
    private String imageUri;

    @NotNull
    private boolean isFound;

}
