package com.todeb.bkolay.creditapplication.service;

import com.todeb.bkolay.creditapplication.message.CustomerInformation;
import com.todeb.bkolay.creditapplication.model.dto.CreditDTO;
import com.todeb.bkolay.creditapplication.model.dto.CustomerDTO;
import com.todeb.bkolay.creditapplication.model.entity.Credit;
import com.todeb.bkolay.creditapplication.model.entity.CreditAppStatus;
import com.todeb.bkolay.creditapplication.model.entity.CreditResult;
import com.todeb.bkolay.creditapplication.model.entity.Customer;
import com.todeb.bkolay.creditapplication.model.mapper.CustomerMapper;
import com.todeb.bkolay.creditapplication.repository.CreditRepository;
import com.todeb.bkolay.creditapplication.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @Mock
    private CreditRepository creditRepository;

    @Mock
    private CustomerInformation customerInformation;

    @Mock
    private CreditLimitCalculatorService creditLimitCalculator;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    @InjectMocks
    private CreditService creditService;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCreditApplication() {
        // init step (Beklenilen sonucu)
        Credit expCredit = getSampleTestCreditApplication().get(0);

        //stub - when step
        when(creditRepository.save(expCredit)).thenReturn(expCredit);

        //then - validate step
        Customer customer1 = new Customer("24506231362","Ahmet","Birinci",25,"+905369378309",7500.00,550,null);
        Credit actualCreditApplication = creditService.createCreditApplication(customer1);

        Assert.assertEquals(expCredit.getCreditResult(),actualCreditApplication.getCreditResult());
        verify(creditRepository,times(1)).save(any());
    }

    @Test
    void getActiveAndApprovedCreditApplication() {
        Customer customer = new Customer("24506231362","Ahmet","Birinci",25,"+905369378309",7500.00,550,null);
        Credit credit = getSampleTestCreditApplication().get(0);

        // stub - when step
        creditService.createCreditApplication(customer);
        when(creditRepository.findAll()).thenReturn(Arrays.asList(credit));

        creditService.getActiveAndApprovedCreditApplication(customer);
        Assert.assertEquals(credit.getCreditAppStatus(),CreditAppStatus.ACTIVE);
    }

    @Test
    void updateNotFinishedCreditApplication() {
        // init step (Beklenilen sonucu)
        Customer customer = new Customer("24506231362","Ahmet","Birinci",25,"+905369378309",7500.00,550,null);
        Credit credit = getSampleTestCreditApplication().get(0);

        // stub - when step
        creditService.createCreditApplication(customer);
        when(creditRepository.save(credit)).thenReturn(credit);
        when(creditRepository.findAll()).thenReturn(Arrays.asList(credit));
        creditService.updateNotFinishedCreditApplication(customer);

        Assert.assertEquals(credit.getCreditResult(),CreditResult.ACCEPTED);

    }

    @Test
    void deleteCreditApplication() {

        // init step
        Customer customer = new Customer("24506231362","Ahmet","Birinci",25,"+905369378309",7500.00,550,null);
        Credit credit = getSampleTestCreditApplication().get(0);

        // stub - when step
        creditService.createCreditApplication(customer);
        when(creditRepository.findAll()).thenReturn(Arrays.asList(credit));

        // then - validate step
        creditService.deleteCreditApplication(customer);
        Mockito.verify(creditRepository).delete(credit);
    }

    private List<Credit> getSampleTestCreditApplication() {
        List<Credit> expCreditApplicationList = new ArrayList<>();
        Customer customer1 = new Customer("24506231362","Ahmet","Birinci",25,"+905369378309",7500.00,550,null);
        Customer customer2 = new Customer("24506231363","Mehmet","İkinci",22,"+905369378310",5500.00,720,null);
        Customer customer3 = new Customer("24506231364","Hasan","Üçüncü",35,"+905369378308",1200.00,195,null);
        Credit creditApplication1 = new Credit(CreditResult.RESULT_IS_AWAITED,null, CreditAppStatus.ACTIVE,customer1);
        Credit creditApplication2 = new Credit(CreditResult.RESULT_IS_AWAITED,null, CreditAppStatus.ACTIVE,customer2);
        Credit creditApplication3 = new Credit(CreditResult.RESULT_IS_AWAITED,null, CreditAppStatus.ACTIVE,customer3);

        expCreditApplicationList.add(creditApplication1);
        expCreditApplicationList.add(creditApplication1);
        expCreditApplicationList.add(creditApplication1);
        return expCreditApplicationList;
    }
}