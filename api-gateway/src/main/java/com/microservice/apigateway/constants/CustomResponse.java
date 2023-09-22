package com.microservice.apigateway.constants;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class CustomResponse {

    public static ResponseEntity<?> getCustomResponse(Map<String,Object> message, HttpStatus httpStatus){

        return new ResponseEntity<>(message,httpStatus);
    }
}
