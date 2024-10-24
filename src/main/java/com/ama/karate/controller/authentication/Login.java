package com.ama.karate.controller.authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.AuthDto;

@RestController
public class Login {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDto loginRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Authenticate user using AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNo(), loginRequest.getPassword())
            );

            System.out.println("Authentication successful for " + authentication.toString());
            if (authentication.isAuthenticated()) {
                response.put("status", "success");
                response.put("message", "Login successful");
                return ResponseEntity.accepted().body(response);
            } else {
                response.put("status", "error");
                response.put("message", "Authorization failed");
                return ResponseEntity.status(401).body(response);
            }
        } catch (AuthenticationException e) {
            response.put("status", "error");
            response.put("message", "Authentication failed");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> test(){
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Login successful");
        return ResponseEntity.accepted().body(response);
    }

}
