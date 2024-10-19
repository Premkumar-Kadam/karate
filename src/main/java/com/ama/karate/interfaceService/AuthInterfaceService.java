package com.ama.karate.interfaceService;

import org.springframework.stereotype.Service;

import com.ama.karate.dto.AuthDto;

@Service
public interface  AuthInterfaceService{

    boolean isAuthenticated(AuthDto reqAuthDto);

    boolean setSessionInRedis(String sessionKey, String phoneNo);

}
