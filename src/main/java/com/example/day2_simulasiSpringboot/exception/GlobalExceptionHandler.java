package com.example.day2_simulasiSpringboot.exception;

import com.example.day2_simulasiSpringboot.dto.response.BaseResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AnalysisNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleNotFound(AnalysisNotFoundException exception){
        return new ResponseEntity<>(BaseResponse.error(exception.getMessage()), HttpStatus.NOT_FOUND);
        //Tetap menggunakan Base Response untuk wrap success dan failed response nya.
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<BaseResponse<Object>> handleDataExisted(DataAlreadyExistException exception){
        return new ResponseEntity<>(BaseResponse.error(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidation(MethodArgumentNotValidException validException){
        List<String> message = new ArrayList<>();
        for(FieldError fieldError : validException.getBindingResult().getFieldErrors()){
            message.add(fieldError.getDefaultMessage());
        }
        return build(HttpStatus.BAD_REQUEST, String.join(",", message));
    }

    @ExceptionHandler(QuoteUnavailableException.class)
    public ResponseEntity<BaseResponse<Object>> handleQuoteUnavailable(QuoteUnavailableException exception){
        log.info("QuoteUnavailableException: {}", exception.getMessage());
        return build(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<Object>> handleConstraintViolation (ConstraintViolationException exception){
        List<String> messages = new ArrayList<>();
        for(ConstraintViolation<?> violation: exception.getConstraintViolations()){
            messages.add(violation.getMessage());
        }
        return build(HttpStatus.BAD_REQUEST, String.join(",", messages));
    }

    @ExceptionHandler(QuoteNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleQuoteNotFound (QuoteNotFoundException exception){
        return build(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    private ResponseEntity<BaseResponse<Object>> build (HttpStatus status, String message){
        return ResponseEntity.status(status).body(BaseResponse.error(message));
    }
}
