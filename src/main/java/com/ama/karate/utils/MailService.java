package com.ama.karate.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ama.karate.dto.ResponseDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public ResponseDto sendEmail(String toEmail, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        ResponseDto response = new ResponseDto();
        try {

            
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom("karate-support@ama.com");
            mailSender.send(message);

            response.setStatusCode(200);
            response.setMessage("Email Sent Successfully !");
            return  response;

        } catch (MessagingException e) {
            e.printStackTrace();
            response.setStatusCode(500);
            response.setMessage("Internal server error !");
            return  response;
        }
    }
}
