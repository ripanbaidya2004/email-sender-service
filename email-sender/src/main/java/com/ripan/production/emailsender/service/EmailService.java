package com.ripan.production.emailsender.service;

import java.io.File;

public interface EmailService {

    void sendEmail(String to, String subject, String message);

    void sendEmailToMultipleUsers(String[] to, String subject, String message);

    void sendEmailWithHtmlContent(String to, String subject, String htmlContent);

    void sendEmailWithFile(String to, String subject, String message, File file);
}
