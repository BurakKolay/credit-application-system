package com.todeb.bkolay.creditapplication.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credits")
public class Credit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id",nullable = false,updatable = false)
    private Long creditId;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "creadit_date",nullable = false)
    private LocalDate creditDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_result")
    private CreditResult creditResult;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_app_status")
    private CreditAppStatus creditAppStatus;


    @NotNull
    @JoinColumn(name = "identity_number", referencedColumnName = "identity_number")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;


    @Transient  //Database'de olmaması için yazıldı.
    private Integer creditLimitMultiplier=4;

    public Credit(CreditResult creditResult, Double creditLimit, CreditAppStatus creditAppStatus, Customer customer) {
        this.creditResult = creditResult;
        this.creditLimit = creditLimit;
        this.creditAppStatus = creditAppStatus;
        this.customer = customer;
    }

}
