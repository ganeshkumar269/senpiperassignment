package com.example.restservice.exception;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

// import org.hibernate.validator.internal.util.logging.Log_.logger;
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
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
		logger.info("Ex.GetMessage: " + ex.getMessage());
		List<String> details = new ArrayList<String>();
		logger.info(request.toString());
		details.add(request.getContextPath());
		ErrorResponse error = new ErrorResponse(ex.getMessage(),details);
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleValueNotFoundException(ValueNotFoundException ex, WebRequest request) {
		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error  = new ErrorResponse("value cannot be null",details);
		return new ResponseEntity<ErrorResponse>(error,HttpStatus.BAD_REQUEST);
	}

	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
    	ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			// ErrorResponse error = new ErrorResponse(fieldName + " err
			errors.put(fieldName, errorMessage);
    	});
		
        // ErrorResponse error = new ErrorResponse("MethodArgumentNotValidException", details);
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
		// return errors;
	}

}