package com.microservice.apigateway.services.jwt;


import com.microservice.apigateway.entities.User;
import com.microservice.apigateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //Write Logic to get the user from the DB
        User user = userRepository.findFirstByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found",null);
        }

        List<String> rolesList = new ArrayList<>();

        List<GrantedAuthority> grantedAuthorities =  rolesList.stream().map(roles -> new SimpleGrantedAuthority("ROLE_ADMIN")).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
