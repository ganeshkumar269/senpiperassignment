package com.example.restservice.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.slf4j.LoggerFactory;

 
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
 
@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler 
{
	org.slf4j.Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<List<String>> handleAllExceptions(Exception ex, WebRequest request) {
        logger.info("handlerAllExceptions" + ex.getStackTrace().toString());
		List<String> details = new ArrayList<>();
		
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<List<String>>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
    	ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
    	});
		
        // ErrorResponse error = new ErrorResponse("MethodArgumentNotValidException", details);
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
		// return errors;
	}

}