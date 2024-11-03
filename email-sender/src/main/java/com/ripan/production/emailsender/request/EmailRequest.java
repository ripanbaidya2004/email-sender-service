package com.ripan.production.emailsender.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailRequest {

    private String[] to;
    private String subject;
    private String message;
    private String htmlContent;
    private MultipartFile file;
}
