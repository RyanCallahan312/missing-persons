package com.azware.missingpersons.dto;

import java.time.Instant;

public class CreateReportRequest {

    private String missingPersonName;

    private String reporterName;
    
    private String description;

    private Instant reportTime;
    
    private Instant lastSeenTime;

    private String lastKnownLocation;

    private String additionalInfo;

    private String imageURI;

}
