package com.ripan.production.emailsender.controller;

import com.ripan.production.emailsender.request.EmailRequest;
import com.ripan.production.emailsender.response.EmailResponse;
import com.ripan.production.emailsender.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @PostMapping("/send")
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody EmailRequest request){
        try{
            if(request.getTo().length > 1) {
                emailService.sendEmailToMultipleUsers(request.getTo(), request.getSubject(), request.getMessage());
            } else{
                emailService.sendEmail(request.getTo()[0], request.getSubject(), request.getMessage());
            }
            EmailResponse successResponse = new EmailResponse("Email sent successfully!", true);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }catch(Exception e){
            EmailResponse failureResponse = new EmailResponse("Error while sending email", false);
            return new ResponseEntity<>(failureResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/send-with-html")
    public ResponseEntity<EmailResponse> sendEmailWithHtmlContent(@RequestBody EmailRequest request){
        try{
            emailService.sendEmailWithHtmlContent(request.getTo()[0], request.getSubject(), request.getMessage());
            EmailResponse successResponse = new EmailResponse("Email sent successfully!", true);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }catch (Exception e){
            EmailResponse failureResponse = new EmailResponse("Error while sending email", false);
            return new ResponseEntity<>(failureResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/send-with-attachment", consumes = "multipart/form-data")
    public ResponseEntity<EmailResponse> sendEmailWithFile(@RequestPart("emailrequest") EmailRequest request, @RequestPart("file")MultipartFile file) {
        try{
            // convert multipartfile to file if needed.
            File tempFile = new File(System.getProperty("java.io.tempdir")+System.currentTimeMillis()+"_"+file.getOriginalFilename());
            file.transferTo(tempFile);

            emailService.sendEmailWithFile(request.getTo()[0], request.getSubject(), request.getMessage(), tempFile);

            EmailResponse succesResponse = new EmailResponse("Email sent successfullt", true);
            return new ResponseEntity<>(succesResponse, HttpStatus.OK);
        }catch (Exception e){
                EmailResponse failureResponse = new EmailResponse("Error while sending email", false);
                return new ResponseEntity<>(failureResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
