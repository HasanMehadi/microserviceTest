package com.microservice.apigateway.dto;

import lombok.Data;

@Data
public class AuthenticationDTO {

    private String email;

    private String password;

}
