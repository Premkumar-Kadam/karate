package com.ama.karate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ama.karate.dao.authentication.loginDao;
import com.ama.karate.dto.AuthDto;
import com.ama.karate.dto.ResponseDto;
import com.ama.karate.dto.SessionDto;
import com.ama.karate.interfaceService.AuthInterfaceService;
import com.ama.karate.utils.Helper;
import com.ama.karate.utils.HtmlTemplateBuilder;
import com.ama.karate.utils.MailService;
import com.ama.karate.utils.PasswordService;
import com.ama.karate.utils.RedisService;

@Service
public class AuthService implements AuthInterfaceService{

    @Autowired loginDao login;

    @Autowired PasswordService passwordService;

    @Autowired RedisService rs;

    @Autowired Helper helper;

    @Autowired HtmlTemplateBuilder htb;

    @Autowired MailService ms;

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
        boolean sessionRes = rs.setSession(sessionKey, sessionData.toString());
        return sessionRes;
    }

    @Override
    public String getSessionInRedis(String sessionKey){
        String sessionRes = rs.getSession(sessionKey);
        return sessionRes;
    }

    @Override
    public ResponseDto forgotPasswordService(String phoneNo){

        ResponseDto responseDto = new ResponseDto();
        try {

            AuthDto email = login.bringUserEmail(phoneNo);
            String OTP = helper.generateOTP(6);
            String body = htb.buildOtpTemplate(email.getEmail(), OTP);
            boolean insertSuccess = login.insertOtp(phoneNo, OTP, "forgit-password");
            if(insertSuccess){
                ResponseDto mailResponse = ms.sendEmail(email.getEmail(),"Forgot Password",body);
                return  mailResponse;
            }
            
            responseDto.setStatusCode(400);
            responseDto.setMessage("Insert Failed");
            return responseDto;
            
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setMessage("Internal Server Error");
            return responseDto;
        }
    }

    @Override
    public ResponseDto changePasswordService(AuthDto user){

        ResponseDto responseDto = new ResponseDto();
        try {

            if(!user.getPassword().equals(user.getConfirmPassword())){
                responseDto.setStatusCode(409);
                responseDto.setMessage("Passwords do not match");
                return responseDto;
            }

            boolean otpVerified = login.isOtpValid(user.getPhoneNo(), user.getOtp());

            if(!otpVerified){
                responseDto.setStatusCode(409);
                responseDto.setMessage("OTP Verification Failed");
                return responseDto;
            }

            boolean passwordStatus = login.updatePassword(passwordService.createPassword(user.getPassword()), user.getPhoneNo());

            if(passwordStatus){
                responseDto.setStatusCode(200);
                responseDto.setMessage("Password Updated Successfully!");
                return responseDto;
            }
            
            responseDto.setStatusCode(500);
            responseDto.setMessage("Operation Error");
            return responseDto;
            
        } catch (Exception e) {
            responseDto.setStatusCode(500);
            responseDto.setMessage("Internal Server Error");
            return responseDto;
        }
    }
}
