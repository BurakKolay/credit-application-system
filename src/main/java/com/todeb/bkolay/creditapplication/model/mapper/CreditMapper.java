package com.todeb.bkolay.creditapplication.model.mapper;

import com.todeb.bkolay.creditapplication.model.dto.CreditDTO;
import com.todeb.bkolay.creditapplication.model.entity.Credit;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Mapper(componentModel  = "spring")
@Component
public class CreditMapper {
    public CreditDTO toDTO(Credit credit) {
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setCreditDate(credit.getCreditDate());
        creditDTO.setCreditLimit(credit.getCreditLimit());
        creditDTO.setCreditResult(credit.getCreditResult());
        creditDTO.setCreditAppStatus(credit.getCreditAppStatus());
        return creditDTO;
    }

    public Credit toEntity(CreditDTO creditDTO) {

        Credit credit = new Credit();
        credit.setCreditDate(creditDTO.getCreditDate());
        credit.setCreditLimit(creditDTO.getCreditLimit());
        credit.setCreditResult(creditDTO.getCreditResult());
        credit.setCreditAppStatus(creditDTO.getCreditAppStatus());
        return credit;
    }
}
