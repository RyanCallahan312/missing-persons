package com.azware.missingpersons.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Data
public class SpecificationRequest {

    @NotNull
    private List<FilterDTO> filters;

    // this indicates if filters are joined together with an and/or
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isFiltersOrOperation;

    @NotNull
    private List<SortDTO> sorts;

    @NotNull
    private PageDTO page;

    public boolean isFiltersOrOperation() {
        return this.isFiltersOrOperation;
    }

    public void setFiltersOrOperation(boolean value) {
        this.isFiltersOrOperation = value;
    }
}
