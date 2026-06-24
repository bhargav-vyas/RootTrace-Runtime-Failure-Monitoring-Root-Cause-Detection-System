package com.bhargav.roottrace.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendCriticalAlert(String errorMessage) {

        try {

            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo("YOUR_REAL_GMAIL@gmail.com");

            mail.setSubject("🚨 RootTrace Critical Error Alert");

            mail.setText(errorMessage);

            System.out.println("Sending email alert...");

            mailSender.send(mail);

            System.out.println("Email sent successfully");

        } catch (Exception e) {

            System.out.println("Email sending failed");

            e.printStackTrace();
        }
    }
}