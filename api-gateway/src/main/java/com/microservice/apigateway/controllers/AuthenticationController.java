package com.microservice.apigateway.controllers;



import com.microservice.apigateway.constants.CustomResponse;
import com.microservice.apigateway.dto.AuthenticationDTO;
import com.microservice.apigateway.dto.SignupDTO;
import com.microservice.apigateway.dto.UserDTO;
import com.microservice.apigateway.services.auth.AuthService;
import com.microservice.apigateway.services.jwt.UserDetailsServiceImpl;
import com.microservice.apigateway.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getEmail());

        final String accessToken = jwtUtil.generateToken(userDetails);

        final String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        Map<String,Object> authResponse = new HashMap<>();
        authResponse.put("access-token",accessToken );
//        authResponse.put("refresh-token",refreshToken);
//        authResponse.put("roles",userDetails.getAuthorities());
        return CustomResponse.getCustomResponse(authResponse, HttpStatus.ACCEPTED);

    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        jwtUtil.validateToken(token);
        return "Token is valid";
    }

    @PostMapping("/register")
    public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO) {
        UserDTO createdUser = authService.createUser(signupDTO);
        if (createdUser == null){
            return new ResponseEntity<>("User not created, come again later!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


}
