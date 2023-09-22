package com.microservice.apigateway.services.auth;



import com.microservice.apigateway.dto.SignupDTO;
import com.microservice.apigateway.dto.UserDTO;
import com.microservice.apigateway.entities.User;
import com.microservice.apigateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        user.setIsActive(true);
        user.setIsExpired(false);
        User createdUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
        userDTO.setName(createdUser.getName());
        return userDTO;
    }
}
