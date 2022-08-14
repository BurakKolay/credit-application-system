package com.todeb.bkolay.creditapplication.controller;

import com.todeb.bkolay.creditapplication.exception.CreditApplicationException;
import com.todeb.bkolay.creditapplication.message.CustomerInformation;
import com.todeb.bkolay.creditapplication.model.dto.CreditDTO;
import com.todeb.bkolay.creditapplication.model.entity.Credit;
import com.todeb.bkolay.creditapplication.model.entity.Customer;
import com.todeb.bkolay.creditapplication.model.mapper.CreditMapper;
import com.todeb.bkolay.creditapplication.service.CreditObtainService;
import com.todeb.bkolay.creditapplication.service.CreditService;
import com.todeb.bkolay.creditapplication.service.CustomerService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/credit")
@AllArgsConstructor
public class CreditController {
    private final CreditMapper creditMapper;
    private final CreditService creditService;
    private final CustomerService customerService;

    private final CustomerInformation customerInformation;
    private final CreditObtainService creditObtainService;

    @PostMapping("/create/{identityNumber}")
    public Credit createCreditApplication(@PathVariable("identityNumber") String identityNumber){
        Customer byIdentityNumber = customerService.getCustomerByIdentityNumber(identityNumber);
        if(creditService.inquireActiveCreditApplication(byIdentityNumber)){
            log.error("You have an incomplete loan application.");
            throw new CreditApplicationException("You have an incomplete loan application.");
        }
        log.info("Credit application created successfully");
        return creditService.createCreditApplication(byIdentityNumber);
    }

    @GetMapping("/{identityNumber}")
    public CreditDTO getActiveAndAcceptedApplicationByCustomer(@PathVariable("identityNumber") String identityNumber){
        Customer customerByIdentityNumber = customerService.getCustomerByIdentityNumber(identityNumber);
        return creditMapper.toDTO(creditService.getActiveAndApprovedCreditApplication(customerByIdentityNumber));
    }
    
    @PutMapping("/get/{identityNumber}")
    public String getCreditApplication(@PathVariable("identityNumber") String identityNumber) throws NotFoundException {
        return creditObtainService.getObtainCredit(creditMapper.toEntity(getActiveAndAcceptedApplicationByCustomer(identityNumber)));
    }

    @DeleteMapping("/delete/{identityNumber}")
    public String deleteCreditApplication(@PathVariable("identityNumber") String identityNumber){
        Customer customerByIdentityNumber = customerService.getCustomerByIdentityNumber(identityNumber);
        return creditService.deleteCreditApplication(customerByIdentityNumber);
    }

    @PutMapping("/update/{identityNumber}")
    public String updateCreditApplication(@PathVariable("identityNumber") String identityNumber){
        Customer customerByIdentityNumber = customerService.getCustomerByIdentityNumber(identityNumber);
        return creditService.updateNotFinishedCreditApplication(customerByIdentityNumber);
    }
}
