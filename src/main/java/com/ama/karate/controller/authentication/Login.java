package com.ama.karate.controller.authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.AuthDto;
import com.ama.karate.interfaceService.AuthInterfaceService;
import com.ama.karate.utils.Helper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class Login {

    @Autowired AuthInterfaceService authService;

    @Autowired private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthDto loginRequest, HttpSession session) {

        System.out.println("Login  request: " + loginRequest.toString());
        boolean isAuthenticated = authService.isAuthenticated(loginRequest);
        Map<String, Object> response = new HashMap<>();
        if (isAuthenticated){

            String sessionKey = Helper.generateRandomString(8);
            boolean sessionRes = authService.setSessionInRedis(sessionKey, loginRequest.getPhoneNo());
            session.setAttribute("phoneNo", loginRequest.getPhoneNo());
            session.setAttribute("authenticated", true);
            session.setAttribute("sessionKey", sessionKey);
            String redisValue = authService.getSessionInRedis(sessionKey);
            System.out.println("Redis value: " + redisValue);

            if(sessionRes){
                response.put("status", "success");
                response.put("message", "login successful");
                return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            }else{
                response.put("status", "error");
                response.put("message", "session creation failed");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }else{
            response.put("status", "error");
            response.put("message", "authorization failed");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthDto authRequest, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(authRequest.getPhoneNo(), authRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                                              SecurityContextHolder.getContext());

            return ResponseEntity.ok("Authenticated");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logged out");
    }



}
