package com.ama.karate.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    // bcript password encoder service
    public PasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // create a new password
    public String createPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // compare password
    public boolean comparePassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    public static void main(String[] args) {
        PasswordService passwordService = new PasswordService();
        String password = passwordService.createPassword("pass@123");
        System.out.println("password: " + password);
        boolean result = passwordService.comparePassword("pass@123", "$2a$10$kZ.rgnyhaNSCf0gaK6CE.egqfiyEZoY5cVe0fV/cvzlj5fCODaXYa");
        System.out.println("result: " + result);
    }
}
