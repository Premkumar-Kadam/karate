package com.ama.karate.utils;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class Helper {

    private static final SecureRandom RANDOM = new SecureRandom();
    public static String generateRandomString(int length) {
    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(randomIndex));
        }
        return result.toString();
    }

    public String generateOTP(int length) {
    String CHARACTERS = "0123456789";
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(randomIndex));
        }
        return result.toString();
    }
}
