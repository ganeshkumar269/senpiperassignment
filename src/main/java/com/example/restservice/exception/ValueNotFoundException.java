package com.example.restservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;



// @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="value not found")
public class ValueNotFoundException extends RuntimeException{
    // @ExceptionHandler(valueNotFoundException.class)
    // public ErrorResponse handleValueNotFoundException
    static final long serialVersionUID = 0L;
    public ValueNotFoundException(String msg){
        super(msg);
    }
    public ValueNotFoundException(){super();}
}