package com.mercadolibre.be_java_hisp_w24_g02.exception;

import com.mercadolibre.be_java_hisp_w24_g02.dto.ExceptionDAO;
import com.mercadolibre.be_java_hisp_w24_g02.dto.FieldExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDAO> notFound(Exception e){
        ExceptionDAO exceptionDAO = new ExceptionDAO(e.getMessage());
        return new ResponseEntity<>(exceptionDAO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDAO> badRequest(BadRequestException e){
        ExceptionDAO exceptionDAO = new ExceptionDAO(e.getMessage());
        return new ResponseEntity<>(exceptionDAO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldExceptionDTO>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FieldExceptionDTO> exceptions = fieldErrors.stream().map(fieldError ->
                new FieldExceptionDTO(fieldError.getDefaultMessage(), fieldError.getField(), fieldError.getCode())
        ).toList();
        return ResponseEntity.badRequest().body(exceptions);
    }
}
