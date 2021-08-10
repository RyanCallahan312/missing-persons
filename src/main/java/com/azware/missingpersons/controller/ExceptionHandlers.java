package com.azware.missingpersons.controller;

import com.azware.missingpersons.context.RequestContext;
import com.azware.missingpersons.dto.BadRequestResponse;
import com.azware.missingpersons.exception.InvalidSearchCriteriaException;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);
    private final RequestContext requestContext;

    @Autowired
    public ExceptionHandlers(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, InvalidSearchCriteriaException.class,
            InvalidDataAccessApiUsageException.class, IllegalArgumentException.class,
            PropertyReferenceException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BadRequestResponse handleInvalidArgumentsExceptions(RuntimeException e) {

        logger.info("Bad request parameters,  responding with 404; Correlation ID {} ",
                this.requestContext.getCorrelationId());
        String message = "Bad Request";
        String stackTrace = Arrays.toString(e.getStackTrace());
        BadRequestResponse response = new BadRequestResponse(message, stackTrace);
        return response;
    }
}
