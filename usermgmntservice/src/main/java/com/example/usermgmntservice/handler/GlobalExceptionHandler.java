package com.example.usermgmntservice.handler;

import com.example.usermgmntservice.dto.GlobalAPIResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(new GlobalAPIResponse(false, ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new GlobalAPIResponse(false, ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
