package com.azware.missingpersons.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SearchOperation {
    EQUALS, IN, LIKE;

    private static final Map<String, SearchOperation> nameToValueMap = new HashMap<>();

    static {
        for (SearchOperation value : EnumSet.allOf(SearchOperation.class)) {
            nameToValueMap.put(value.name(), value);
        }
    }

    public static SearchOperation forName(String name) {
        return nameToValueMap.get(name);
    }
}