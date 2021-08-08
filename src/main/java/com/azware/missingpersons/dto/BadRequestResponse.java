package com.azware.missingpersons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BadRequestResponse {

    private String message;

    private String stackTrace;

}
