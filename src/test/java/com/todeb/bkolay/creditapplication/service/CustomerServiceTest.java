package com.todeb.bkolay.creditapplication.service;

import com.todeb.bkolay.creditapplication.exception.EntityNotFoundException;
import com.todeb.bkolay.creditapplication.model.dto.CustomerDTO;
import com.todeb.bkolay.creditapplication.model.entity.Customer;
import com.todeb.bkolay.creditapplication.repository.CustomerRepository;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;


    @Test
    void getAllCustomer() {

        // init step (Beklenilen sonuç)
        List<Customer> expCustomerList = getSampleTestCustomers();

        //stub - when stub
        Mockito.when(customerRepository.findAll()).thenReturn(expCustomerList); //Eğer ki CustomerService içindeki findall metodu
                                                                                //çağırırsa mockito bizim için expCustomerList çağıracak.

        //then - validate step
        List<Customer> actualCustomerList = customerService.getAllCustomer();

        Assert.assertEquals(expCustomerList.size(),actualCustomerList.size());

        System.out.println("First: " + expCustomerList);
        for (int i = 0; i < expCustomerList.size(); i++) {
            Customer currExpCustomer = expCustomerList.get(i);
            Customer currActualCustomer = actualCustomerList.get(i);
            Assert.assertEquals(currExpCustomer.getIdentityNumber(),currActualCustomer.getIdentityNumber());
            Assert.assertEquals(currExpCustomer.getName(),currActualCustomer.getName());
        }
        System.out.println("Second: "+ expCustomerList);
    }

    @Test
    void getCustomerByIdentityNumber_successfull() {
        //init step
        Customer expectedCustomer = getSampleTestCustomers().get(1);
        Optional<Customer> opExpectedCustomer = Optional.of(expectedCustomer);
        //stub - when step
        Mockito.when(customerRepository.findByIdentityNumber(Mockito.any())).thenReturn(opExpectedCustomer);
        //then - validate step
        Customer actualCustomer = customerService.getCustomerByIdentityNumber("24506231362");

        Assert.assertEquals(actualCustomer.getIdentityNumber(),expectedCustomer.getIdentityNumber());
        Assert.assertEquals(actualCustomer.getName(),expectedCustomer.getName());
    }

    @Test
    void getCustomerByIdentityNumber_failure() {
        //stub - when step
        Mockito.when(customerRepository.findByIdentityNumber("24506231362")).thenReturn(Optional.empty());
        //then - validate step
        assertThrows(RuntimeException.class,
                () -> {
                    Customer actCustomer = customerService.getCustomerByIdentityNumber("24506231362");
                });
    }

    @Test
    void create() {
        // init step (Beklenilen sonucu)
        Customer customer1 = getSampleTestCustomers().get(0);

        //stub - when step
        Mockito.when(customerRepository.save(customer1)).thenReturn(customer1);
        //then - validate step
        CustomerDTO customerDTO= new CustomerDTO();
        customerDTO.setIdentityNumber(customer1.getIdentityNumber());
        customerDTO.setName(customer1.getName());
        customerDTO.setSurname(customer1.getSurname());
        customerDTO.setAge(customer1.getAge());
        customerDTO.setPhoneNumber(customer1.getPhoneNumber());
        customerDTO.setSalary(customer1.getSalary());
        customerDTO.setCreditScore(customer1.getCreditScore());
        Customer customer2 = customerService.create(customerDTO);

        //Mockito.verify(productRepository, Mockito.times(1)).save(product1);
        Assert.assertEquals(customer1.getName(),customer2.getName());

    }

    @Test
    void delete() {
        //init step
        Customer customer1 = getSampleTestCustomers().get(0);

        //stub - when step
        Mockito.when(customerRepository.findByIdentityNumber(customer1.getIdentityNumber())).thenReturn(Optional.of(customer1));

        //then - validate step
        customerService.delete(customer1.getIdentityNumber());
        //ptional<Customer> byId = customerRepository.findByIdentityNumber(customer1.getIdentityNumber());
        Mockito.verify(customerRepository).delete(customer1);
    }

    @Test
    void updateCustomer() {
        // init step
        Customer customer1 = getSampleTestCustomers().get(0);

        CustomerDTO customerDTO= new CustomerDTO();
        customerDTO.setIdentityNumber("24506231362");
        customerDTO.setName("Vedat");
        customerDTO.setSurname("Kolay");
        customerDTO.setAge(25);
        customerDTO.setPhoneNumber("+905786321536");
        customerDTO.setSalary(5000.00);
        customerDTO.setCreditScore(950);

        // stub - when stub
        Mockito.when(customerRepository.findByIdentityNumber(customer1.getIdentityNumber())).thenReturn(Optional.of(customer1));
        customerService.updateCustomer(customer1.getIdentityNumber(),customerDTO);

        //then - validate step
        Assert.assertEquals(customer1.getIdentityNumber(),customerDTO.getIdentityNumber());
        Assert.assertEquals(customer1.getName(),customerDTO.getName());
        Assert.assertEquals(customer1.getPhoneNumber(),customerDTO.getPhoneNumber());
    }

    private List<Customer> getSampleTestCustomers() {
        List<Customer> expCustomerList = new ArrayList<>();
        Customer customer1 = new Customer("24506231362","Ahmet","Birinci",25,"+905369378309",7500.00,550,null);
        Customer customer2 = new Customer("24506231363","Mehmet","İkinci",22,"+905369378310",5500.00,720,null);
        Customer customer3 = new Customer("24506231364","Hasan","Üçüncü",35,"+905369378308",1200.00,195,null);
        expCustomerList.add(customer1);
        expCustomerList.add(customer2);
        expCustomerList.add(customer3);
        return expCustomerList;
    }
}