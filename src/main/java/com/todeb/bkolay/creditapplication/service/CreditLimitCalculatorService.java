package com.todeb.bkolay.creditapplication.service;

import com.todeb.bkolay.creditapplication.model.entity.Credit;
import com.todeb.bkolay.creditapplication.model.entity.CreditLimit;
import com.todeb.bkolay.creditapplication.model.entity.CreditResult;
import com.todeb.bkolay.creditapplication.model.entity.Customer;
import com.todeb.bkolay.creditapplication.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditLimitCalculatorService {

    //private final CreditRepository creditRepository;

    public void creditLimitCalculator(Credit credit){
        Customer creditCustomer = credit.getCustomer();
        Integer creditScore = creditCustomer.getCreditScore();
        Double creditCustomerSalary = creditCustomer.getSalary();
        Integer creditLimitMultiplier = credit.getCreditLimitMultiplier();

        boolean creditLimitStatus = (creditCustomerSalary >= CreditLimit.HIGH.getCreditLimit());
        boolean creditScoreStatusForSpecial = (creditScore >= CreditResult.ACCEPTED.getCreditScoreLimit());

        if(creditScoreStatusForSpecial){
            CreditLimit.SPECIAL.setCreditLimit(creditCustomerSalary * creditLimitMultiplier); //Eğer ki skoru special ise maaş * 4
            credit.setCreditLimit(CreditLimit.SPECIAL.getCreditLimit());
        } else if (creditLimitStatus) {
            credit.setCreditLimit(CreditLimit.HIGH.getCreditLimit());   //Eğer ki skoru special değil ise yani skoru 1000 üzerinde değilse
                                                                        //ama maaşı 5000 üzerinde ve skoru 500 üzerinde ise verildi.
        }else {
            credit.setCreditLimit(CreditLimit.LOW.getCreditLimit());    //Eğer ki skoru 500 üzerinde ama maaşı düşük ise bu verildi.
        }
    }
}
