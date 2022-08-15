package com.todeb.bkolay.creditapplication.model.mapper;

import com.todeb.bkolay.creditapplication.model.dto.CustomerDTO;
import com.todeb.bkolay.creditapplication.model.entity.Credit;
import com.todeb.bkolay.creditapplication.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public static CustomerDTO toDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        customerDTO.setIdentityNumber(customer.getIdentityNumber());
        customerDTO.setSurname(customer.getSurname());
        customerDTO.setAge(customer.getAge());
        customerDTO.setSalary(customer.getSalary());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setCreditScore(customer.getCreditScore());
        return customerDTO;
    }

    public static Customer toEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setIdentityNumber(customerDTO.getIdentityNumber());
        customer.setSurname(customerDTO.getSurname());
        customer.setAge(customerDTO.getAge());
        customer.setSalary(customerDTO.getSalary());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setCreditScore(customerDTO.getCreditScore());
        return customer;
    }
}
