package com.mercadolibre.be_java_hisp_w24_g02.exception;

import com.mercadolibre.be_java_hisp_w24_g02.dao.ExceptionDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ControllerAdvice()
public class GlobalExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDAO> notFound(Exception e){
        ExceptionDAO exceptionDAO = new ExceptionDAO(e.getMessage());
        return new ResponseEntity<ExceptionDAO>(exceptionDAO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDAO> badRequest(BadRequestException e){
        ExceptionDAO exceptionDAO = new ExceptionDAO(e.getMessage());
        return new ResponseEntity<>(exceptionDAO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return ResponseEntity.badRequest().body(fieldErrors.toString());
    }
}
