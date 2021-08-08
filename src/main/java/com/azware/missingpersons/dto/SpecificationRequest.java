package com.azware.missingpersons.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SpecificationRequest {

    @NotNull
    private List<FilterDTO> filters;

    // this indicates if filters are joined together with an and/or
    private boolean isFiltersOrOperation;

    @NotNull
    private List<SortDTO> sorts;

    @NotNull
    private PageDTO page;
}
