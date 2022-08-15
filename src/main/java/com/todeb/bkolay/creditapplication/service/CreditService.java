package com.todeb.bkolay.creditapplication.service;

import com.todeb.bkolay.creditapplication.exception.CreditApplicationException;
import com.todeb.bkolay.creditapplication.message.CustomerInformation;
import com.todeb.bkolay.creditapplication.model.entity.Credit;
import com.todeb.bkolay.creditapplication.model.entity.CreditAppStatus;
import com.todeb.bkolay.creditapplication.model.entity.CreditResult;
import com.todeb.bkolay.creditapplication.model.entity.Customer;
import com.todeb.bkolay.creditapplication.repository.CreditRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;

    private final CustomerInformation customerInformation;
    private final CreditLimitCalculatorService creditLimitCalculator;

    public Credit createCreditApplication(Customer customer){
        Credit creditApplication = new Credit(CreditResult.RESULT_IS_AWAITED,null, CreditAppStatus.ACTIVE,customer);
        creditRepository.save(creditApplication);
        return creditApplication;
    }

    private boolean inquireActiveAndApprovedCreditApplication(Customer customer){
        return creditRepository.findAll().stream()
                .anyMatch(c -> c.getCustomer().equals(customer)                             //Böyle bir müşteri var mı?
                        && c.getCreditAppStatus().equals(CreditAppStatus.ACTIVE)            //Aktif kredi başvurusu var mı?
                        && c.getCreditResult().equals(CreditResult.ACCEPTED));              //Onaylanmış kredi başvurusu var mı?
    }

    public boolean inquireActiveCreditApplication(Customer customer){
        if(inquireActiveAndApprovedCreditApplication(customer)){
            log.error("You have an approved credit application");
            throw new CreditApplicationException("You have an approved credit application");  //Exception yazılacak
        }
        // Böyle bir müşteri var mı ve bu müşterinin aktif kredi başvurusu var mı?
        return creditRepository.findAll().stream()
                .anyMatch(c -> c.getCustomer().equals(customer) && c.getCreditAppStatus().equals(CreditAppStatus.ACTIVE));
    }

    private Credit getActiveCreditApplication(Customer customer){
        return creditRepository.findAll().stream().filter(c->c.getCustomer().equals(customer))           // Bu müşteriden var mı
                .filter(c -> c.getCreditAppStatus() == CreditAppStatus.ACTIVE).findAny()            // Aktif bir kredi başvurusu var mı
                .orElseThrow(() -> new CreditApplicationException("You do not have an active credit application "));            //Yoksa bilgilendirme yap
    }

    public Credit getActiveAndApprovedCreditApplication(Customer customer){
        if(getActiveCreditApplication(customer).getCreditResult().equals(CreditResult.RESULT_IS_AWAITED)){
            //throw new CreditApplicationException("Your credit application is still continues");
        }
        return getActiveCreditApplication(customer);
    }

    private Credit notFinishedCreditApplication(Customer customer){
        return creditRepository.findAll().stream().filter(customer1->customer1.getCustomer().getIdentityNumber().equals(customer.getIdentityNumber()))
                .filter(credit->credit.getCreditResult().equals(CreditResult.RESULT_IS_AWAITED)).findAny()
                .orElseThrow(() -> new CreditApplicationException("You do not have credit application"));
    }

    public String updateNotFinishedCreditApplication(Customer customer){
        Credit creditResulting = notFinishedCreditApplication(customer);
        Integer creditScore = customer.getCreditScore();

        boolean creditScoreStatus = (creditScore >= CreditResult.DENIED.getCreditScoreLimit()); //Kredi skoru 500den büyük ya da eşit
                                                                                                //eşit değilse false döner.
        if(creditScoreStatus){
            log.info("Your credit application is accepted");
            creditResulting.setCreditResult(CreditResult.ACCEPTED);
            creditLimitCalculator.creditLimitCalculator(creditResulting);
        }else {
            log.info("Your credit application is denied");
            creditResulting.setCreditResult(CreditResult.DENIED);
            creditResulting.setCreditAppStatus(CreditAppStatus.PASSIVE);
        }

        creditRepository.save(creditResulting);
        return customerInformation.finishedApplicationMessage(customer.getPhoneNumber(),creditResulting.getCreditLimit());
    }

    public String deleteCreditApplication(Customer customer){
        creditRepository.delete(getActiveAndApprovedCreditApplication(customer));
        return customerInformation.deletedCreditApplication();
    }
}
