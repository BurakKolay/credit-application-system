package com.todeb.bkolay.creditapplication.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todeb.bkolay.creditapplication.model.entity.CreditAppStatus;
import com.todeb.bkolay.creditapplication.model.entity.CreditResult;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
public class CreditDTO {

    @Enumerated(EnumType.STRING)
    private CreditResult creditResult;

    private Double creditLimit;

    @Enumerated(EnumType.STRING)
    private CreditAppStatus creditAppStatus;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate creditDate;
}
