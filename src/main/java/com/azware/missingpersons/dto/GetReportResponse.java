package com.azware.missingpersons.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class GetReportResponse {
    
    private long id;

    private String missingPersonName;

    private String reporterName;
    
    private String description;

    private Instant reportTime;
    
    private Instant lastSeenTime;

    private String lastKnownLocation;

    private String additionalInfo;

    private String imageURI;

}
