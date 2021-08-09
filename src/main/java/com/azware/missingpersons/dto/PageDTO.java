package com.azware.missingpersons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageDTO {

    private int pageNumber;

    private int pageSize;

}
