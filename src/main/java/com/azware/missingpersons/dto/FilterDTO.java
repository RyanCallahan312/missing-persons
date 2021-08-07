package com.azware.missingpersons.dto;

import java.util.List;

import lombok.Data;

@Data
public class FilterDTO {
    
    private String fieldName;
    
    private List<String> values;

    private String operator;
}
