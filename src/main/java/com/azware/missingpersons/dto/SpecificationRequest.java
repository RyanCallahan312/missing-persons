package com.azware.missingpersons.dto;

import java.util.List;

import lombok.Data;

@Data
public class SpecificationRequest {
    
    private List<FilterDTO> filters;

    private SortDTO sort;

    private PageDTO page;
}
