package com.mercadolibre.be_java_hisp_w24_g02.exception;

import com.mercadolibre.be_java_hisp_w24_g02.dao.ExceptionDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDAO> notFound(Exception e){
        ExceptionDAO exceptionDAO = new ExceptionDAO(e.getMessage());
        return new ResponseEntity<>(exceptionDAO, HttpStatus.NOT_FOUND);
    }
}
