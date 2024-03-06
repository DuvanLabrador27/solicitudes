package com.semillero.solicitudes.exceptions.handlers;

import com.semillero.solicitudes.exceptions.ResourceNotComplete;
import com.semillero.solicitudes.exceptions.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.ErrorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {
    private final ErrorDetail errorDetail;
    public HandlerException(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException ex) {
        errorDetail.setDateTime(new Date());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotComplete.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> resourceNotComplete(ResourceNotComplete ex) {
        errorDetail.setDateTime(new Date());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Resource Not Complete");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }

}
