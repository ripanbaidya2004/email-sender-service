package com.ripan.production.emailsender.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    private Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${spring.mail.from.address}")
    private String fromAddress;

    @Override public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom(fromAddress);

        mailSender.send(simpleMailMessage);

        logger.info("Email has been sent successfully to {}", to);
    }

    @Override public void sendEmailToMultipleUsers(String[] to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom(fromAddress);

        mailSender.send(simpleMailMessage);

        logger.info("Email has been sent successfully to {}", to);
    }

    @Override public void sendEmailWithHtmlContent(String to, String subject, String htmlContent) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            // enable multipart mode
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setText(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // here the true flag indicates that its html content
            helper.setFrom(fromAddress);

            mailSender.send(mimeMessage);

            logger.info("Email has been sent successfully to {}", to);
        }catch (MessagingException e){
            System.out.println("Error while sending email.");
        }
    }

    @Override public void sendEmailWithFile(String to, String subject, String message, File file) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message);
            helper.setFrom(fromAddress);

            // determine the mime type from the attachment
            String mimeType = Files.probeContentType(file.toPath());
            if(mimeType == null) mimeType = "application/octet-stream"; // default to binary stream if any stream is unknown

            helper.addAttachment(file.getName(), file);

            logger.info("Email has been sent successfully to {}", to);
        } catch (MessagingException | IOException e) {
            logger.error("Error while sending email to {}: {}", to, e.getMessage());
        }
    }
}
