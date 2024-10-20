package com.ama.karate.controller.authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.AuthDto;
import com.ama.karate.interfaceService.AuthInterfaceService;
import com.ama.karate.utils.Helper;

import jakarta.servlet.http.HttpSession;

@RestController
public class Login {

    @Autowired AuthInterfaceService authService;

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

}
