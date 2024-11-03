package com.ripan.production.emailsender.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@RequiredArgsConstructor
@SpringBootTest
class EmailServiceImplTest {

    private final EmailService emailService;

    @Test
    void emailSendTest(){
        System.out.println("sending mail..");
        emailService.sendEmail("demo@gmail.com", "email for demo purpose", "this is demo message");
    }
}