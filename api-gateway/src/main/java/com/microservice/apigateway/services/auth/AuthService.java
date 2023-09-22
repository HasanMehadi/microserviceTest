package com.microservice.apigateway.services.auth;


import com.microservice.apigateway.dto.SignupDTO;
import com.microservice.apigateway.dto.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupDTO signupDTO);
}
