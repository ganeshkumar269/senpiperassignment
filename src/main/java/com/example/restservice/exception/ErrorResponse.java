package com.example.restservice.exception;

import java.util.List;
// import javax.xml.bind.annotation.XmlRootElement;
import lombok.*;
// @XmlRootElement(name = "error")
public class ErrorResponse 
{
    @Getter
    @Setter
    private String message;
    
    //Specific errors in API request processing
    @Getter
    @Setter
    private List<String> details;
    
    public ErrorResponse(){}
    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
 
    //General error message about nature of error
 
    //Getter and setters
}