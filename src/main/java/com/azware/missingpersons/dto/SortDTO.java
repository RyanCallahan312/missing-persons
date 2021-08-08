package com.azware.missingpersons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortDTO {

    private String fieldName;

    private String direction;
}
