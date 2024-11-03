package com.ripan.production.emailsender.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailResponse {

    private String message;
    private boolean status;

}
