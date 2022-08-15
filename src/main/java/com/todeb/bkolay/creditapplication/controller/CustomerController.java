package com.todeb.bkolay.creditapplication.controller;

import com.todeb.bkolay.creditapplication.model.dto.CustomerDTO;
import com.todeb.bkolay.creditapplication.model.entity.Customer;
import com.todeb.bkolay.creditapplication.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/all")
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/{identityNumber}")
    public ResponseEntity getCustomerByIdentityNumber(@PathVariable("identityNumber") String identityNumber){
        Customer byIdentityNumber;
        try {
            byIdentityNumber = customerService.getCustomerByIdentityNumber(identityNumber);
        }catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(byIdentityNumber);
    }

    @PostMapping("/create")
    public ResponseEntity createNewCustomer(@RequestBody CustomerDTO customer){
        Customer respCustomer = customerService.create(customer);
        if(respCustomer==null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Customer could not be created successfully");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(respCustomer);
    }

    @DeleteMapping("/delete/{identityNumber}")
    public ResponseEntity deleteCustomer(@PathVariable("identityNumber") String identityNumber){
        try{
            customerService.delete(identityNumber);
        }catch (RuntimeException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Related customer deleted successfully");
    }

    @PutMapping("/update/{identityNumber}")
    public ResponseEntity updateCustomer(@PathVariable("identityNumber") String identityNumber,@RequestBody CustomerDTO customer){
        Customer update = customerService.updateCustomer(identityNumber,customer);
        if(update == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer could not be updated successfully");
        }
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    @PutMapping("/creditupdate/{identityNumber}")
    public ResponseEntity updateCustomerCredit(@PathVariable("identityNumber") String identityNumber,@RequestBody Customer customer){
        Customer update = customerService.updateCustomerCreditScore(identityNumber, customer);
        if(update == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer credit score could not be updated successfully");
        }
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }
}
