package com.azware.missingpersons.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilterDTO {
    
    private String fieldName;
    
    private List<Object> values;

    private String operator;
}
