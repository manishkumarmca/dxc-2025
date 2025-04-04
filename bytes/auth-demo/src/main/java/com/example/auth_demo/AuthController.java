package com.example.auth_demo;



import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    // private final UserDetailsService userDetailsService;

    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager,JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        // this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
       
        System.out.println(authRequest);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("incorrect username or password");
        }

        //String currentUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails=new User(authRequest.getUsername(),authRequest.getPassword(),new ArrayList<>());
        String jwt = jwtUtils.createToken(userDetails);
        AuthResponse authResponse = new AuthResponse(jwt);
        return ResponseEntity.ok(authResponse);
        
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public AuthResponse authExceptionHandler(Throwable e) {
        AuthResponse authResponse = new AuthResponse(null);
        authResponse.setMessage(e.getMessage());
        return authResponse;
    }

}