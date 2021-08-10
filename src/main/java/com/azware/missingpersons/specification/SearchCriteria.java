package com.azware.missingpersons.specification;

import java.util.List;

import com.azware.missingpersons.constant.SearchOperation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class SearchCriteria {

    private String key;
    
    private SearchOperation searchOperation;

    private boolean isOrOperation;

    private List<Object> arguments;
}