package com.todeb.bkolay.creditapplication.service;

import com.todeb.bkolay.creditapplication.exception.CustomJwtException;
import com.todeb.bkolay.creditapplication.model.dto.CustomerDTO;
import com.todeb.bkolay.creditapplication.model.entity.Customer;
import com.todeb.bkolay.creditapplication.model.mapper.CustomerMapper;
import com.todeb.bkolay.creditapplication.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private Integer creditScoreCalculator(){
        Random randomScore = new Random();
        return randomScore.nextInt(2000);
    }
    public List<Customer> getAllCustomer(){
        List<Customer> allCustomer = customerRepository.findAll();
        return allCustomer;
    }

    public Customer getCustomerByIdentityNumber(String identityNumber){
        Optional<Customer> byIdentityNumber = customerRepository.findByIdentityNumber(identityNumber);
        if(!byIdentityNumber.isPresent()){
            log.error("Customer not found");
        }
        else log.info("Customer getting");
        return byIdentityNumber.orElseThrow(()-> new RuntimeException("Customer not found"));
    }

    public Customer create(CustomerDTO customerDTO){
        if(customerRepository.existsByIdentityNumber(customerDTO.getIdentityNumber())){
            throw new CustomJwtException("Identity number is already in use", HttpStatus.BAD_REQUEST);
        }
        else if(customerRepository.existsByPhoneNumber(customerDTO.getPhoneNumber())){
            throw new CustomJwtException("Phone number is already in use", HttpStatus.BAD_REQUEST);
        }
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Customer customer1 = customerRepository.save(customer);
        customer1.setCreditScore(creditScoreCalculator());
        return customerRepository.save(customer1);
    }

    public void delete(String identityNumber){
        customerRepository.delete(getCustomerByIdentityNumber(identityNumber));
    }

    public Customer updateCustomer(String identityNumber, CustomerDTO customer){
        Optional<Customer> customerByİd= customerRepository.findByIdentityNumber(identityNumber);
        if(!customerByİd.isPresent()){
            log.error("Customer did not found.");
            return null;
        }
        Customer updatedCustomer = customerByİd.get();
        if(!StringUtils.isEmpty(customer.getPhoneNumber())){
            updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
        }
        if(!StringUtils.isEmpty(customer.getAge())){
            updatedCustomer.setAge(customer.getAge());
        }
        if(!StringUtils.isEmpty(customer.getName())){
            updatedCustomer.setName(customer.getName());
        }
        if(!StringUtils.isEmpty(customer.getSurname())){
            updatedCustomer.setSurname(customer.getSurname());
        }
        if(!StringUtils.isEmpty(customer.getEmail())){
            updatedCustomer.setEmail(customer.getEmail());
        }
        if(!StringUtils.isEmpty(customer.getSalary())){
            updatedCustomer.setSalary(customer.getSalary());
        }
        return customerRepository.save(updatedCustomer);
    }

    public Customer updateCustomerCreditScore(String identityNumber, Customer customer){
        Optional<Customer> customerByİd= customerRepository.findById(identityNumber);
        if(!customerByİd.isPresent()){
            log.error("Customer did not found.");
            return null;
        }
        Customer updatedCustomer = customerByİd.get();
        if(!StringUtils.isEmpty(customer.getCreditScore())){
            updatedCustomer.setCreditScore(customer.getCreditScore());
        }
        return customerRepository.save(updatedCustomer);
    }
}
