package com.todeb.bkolay.creditapplication.service;

import com.todeb.bkolay.creditapplication.model.entity.Credit;
import com.todeb.bkolay.creditapplication.model.entity.CreditAppStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditObtainService {

    public String getObtainCredit(Credit credit){
        credit.setCreditAppStatus(CreditAppStatus.PASSIVE);
        return "Your credit limits is "+ credit.getCreditLimit();
    }
}
