package com.ama.karate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ama.karate.dao.loginDao;
import com.ama.karate.dto.AuthDto;
import com.ama.karate.dto.SessionDto;
import com.ama.karate.interfaceService.AuthInterfaceService;
import com.ama.karate.utils.PasswordService;
import com.ama.karate.utils.RedisService;

@Service
public class AuthService implements AuthInterfaceService{

    @Autowired loginDao login;

    @Autowired PasswordService passwordService;

    @Autowired RedisService rs;

    //to check if user entered password matched the database password
    @Override
    public boolean isAuthenticated(AuthDto reqAuthDto){
        AuthDto dbAuth = login.userPassword(reqAuthDto.getPhoneNo());
        if(dbAuth != null){
            boolean authSuccess = passwordService.comparePassword(reqAuthDto.getPassword(), dbAuth.getPassword());
            return authSuccess;
        }else{
            return false;
        }

    }

    @Override
    public boolean setSessionInRedis(String sessionKey, String phoneNo){
        SessionDto sessionData = login.sessionData(phoneNo);
        boolean sessionRes = rs.setSession(sessionKey, sessionData);
        return sessionRes;
    }
}
