package com.azware.missingpersons.context;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestContext {

    private String correlationId;

}
