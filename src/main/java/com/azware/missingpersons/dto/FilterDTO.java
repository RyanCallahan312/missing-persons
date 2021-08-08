package com.azware.missingpersons.dto;

import java.util.List;

import lombok.Data;

@Data
public class FilterDTO {
    
    private String fieldName;
    
    private List<Object> values;

    private String operator;
}
