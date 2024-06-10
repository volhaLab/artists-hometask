package com.example.hometask.configuration;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler({BadRequestException.class,})
    @ResponseStatus(BAD_REQUEST)
    public Error badRequestExceptionHandler(Exception ex) {

        return logAndConvert(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public Error generalExceptionHandler(Exception ex) {

        return logAndConvert(ex);
    }

    private Error logAndConvert(Exception ex) {

        logger.error("Handling {}, due to {}", ex.getClass().getSimpleName(), ex.getMessage());
        return new Error(ex.getMessage());
    }
}
