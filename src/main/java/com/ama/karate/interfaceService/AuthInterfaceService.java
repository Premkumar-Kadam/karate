package com.ama.karate.interfaceService;

import org.springframework.stereotype.Service;

import com.ama.karate.dto.AuthDto;
import com.ama.karate.dto.ResponseDto;

@Service
public interface  AuthInterfaceService{

    boolean isAuthenticated(AuthDto reqAuthDto);

    boolean setSessionInRedis(String sessionKey, String phoneNo);

    String getSessionInRedis(String sessionKey);

    ResponseDto forgotPasswordService(String phoneNo);

}
