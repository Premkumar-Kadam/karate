package com.ama.karate.controller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ama.karate.dto.AuthDto;
import com.ama.karate.dto.ResponseDto;
import com.ama.karate.interfaceService.AuthInterfaceService;

import jakarta.servlet.http.HttpSession;

@RestController
public class Password {


    @Autowired AuthInterfaceService ais;
    
    @PostMapping("/forgot-password")
    public  ResponseEntity<String> forgitPassword(@RequestBody AuthDto user, HttpSession session){

        ResponseDto response = ais.forgotPasswordService(user.getPhoneNo());
        return ResponseEntity.accepted().body(response.toString());
    }

    
}
