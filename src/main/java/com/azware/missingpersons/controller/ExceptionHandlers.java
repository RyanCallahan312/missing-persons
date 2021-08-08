package com.azware.missingpersons.controller;

import com.azware.missingpersons.dto.BadRequestResponse;
import com.azware.missingpersons.exception.InvalidSearchCriteriaException;

import java.util.Arrays;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler({ MethodArgumentNotValidException.class, InvalidSearchCriteriaException.class,
            InvalidDataAccessApiUsageException.class, IllegalArgumentException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BadRequestResponse handleInvalidArgumentsExceptions(RuntimeException e) {
        String message = "Bad Request";
        return new BadRequestResponse(message, Arrays.toString(e.getStackTrace()));
    }
}
