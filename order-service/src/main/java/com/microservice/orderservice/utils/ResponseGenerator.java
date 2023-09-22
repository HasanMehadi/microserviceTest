package com.microservice.prodectservice.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseGenerator {

    public static ResponseEntity<?> generateResponse(String message, HttpStatus httpStatus){
        return new ResponseEntity<>(message, httpStatus);
    }
}
