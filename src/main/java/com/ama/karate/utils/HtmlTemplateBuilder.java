package com.ama.karate.utils;

import org.springframework.stereotype.Service;

@Service
public class HtmlTemplateBuilder {
    
    public String buildOtpTemplate(String recipientName, String otp) {
        StringBuilder htmlTemplate = new StringBuilder();

        htmlTemplate.append("<!DOCTYPE html>");
        htmlTemplate.append("<html>");
        htmlTemplate.append("<head>");
        htmlTemplate.append("<meta charset=\"UTF-8\">");
        htmlTemplate.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        htmlTemplate.append("<title>Password Reset OTP</title>");
        htmlTemplate.append("</head>");
        htmlTemplate.append("<body style=\"font-family: Arial, sans-serif;\">");

        // Header
        htmlTemplate.append("<h2 style=\"color: #333;\">Hello, ").append(recipientName).append("!</h2>");

        // Body Content
        htmlTemplate.append("<p>We received a request to reset your password. Use the following OTP to proceed:</p>");
        htmlTemplate.append("<h3 style=\"color: #4CAF50; font-size: 24px;\">").append(otp).append("</h3>");
        htmlTemplate.append("<p><b>Note:</b> This OTP is valid for 10 minutes.</p>");
        
        // Security Instructions
        htmlTemplate.append("<ul>");
        htmlTemplate.append("<li>Do not disclose your OTP to anyone, including support personnel.</li>");
        htmlTemplate.append("<li>Ensure you enter the OTP only on our secure website or app.</li>");
        htmlTemplate.append("<li>If you did not request this, please ignore this email or contact support immediately.</li>");
        htmlTemplate.append("</ul>");

        // Footer
        htmlTemplate.append("<p style=\"color: #999; font-size: 12px;\">This is an automated email. Please do not reply.</p>");
        htmlTemplate.append("</body>");
        htmlTemplate.append("</html>");

        return htmlTemplate.toString();
    }
}
