package com.todeb.bkolay.creditapplication.message;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Data
@RestController
public class CustomerInformation {

    public String createdMessage(){
        return "The credit application has been successfully created.";
    }

    public String finishedApplicationMessage(String phoneNumber,Double creditLimit){
        return "Sending SMS to "+phoneNumber+"="+"Your credit application has been finished. Your credit limit is: "+ creditLimit;
    }

    public String deletedCreditApplication(){
        return "Application has been successfully deleted.";
    }
}
