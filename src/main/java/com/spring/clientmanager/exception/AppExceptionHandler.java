package com.spring.clientmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleBusinessException(UserNotFoundException e){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Result Message", e.getMessage());
        return  errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException e){
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return  errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Map<String, String> handleInvalidDuplicateArgument(SQLIntegrityConstraintViolationException e){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("ID/mobile number already exists", e.getMessage());
        return  errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleInvalidUpdateArgument(Exception e){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Please fill in all fields to update", "");
        return  errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> handleRSAIDArgument(RuntimeException e){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Invalid RSA ID", "");
        return  errorMap;
    }
}
