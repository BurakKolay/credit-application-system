package com.todeb.bkolay.creditapplication.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(unique = true, name = "identity_number")
    @Pattern(regexp = "[1-9]\\d{10}", message = "{credit.constraints.identityNumber.Wrong.message}")
    private String identityNumber;

    @NotBlank(message = "{credit.constraints.name.NotBlank.message}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{credit.constraints.name.Wrong.message}")
    private String name;

    @NotBlank(message = "{credit.constraints.surname.NotBlank.message}")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "{credit.constraints.surname.Wrong.message}")
    private String surname;

    @Column(unique = true)
    @NotBlank(message = "{credit.constraints.phoneNumber.Wrong.message}")
    @Pattern(regexp = "\\+?[0-9]*$", message = "{credit.constraints.phoneNumber.Wrong.message}")
    private String phoneNumber;

    @Column(nullable = false)
    //@Pattern(regexp = "^\\d*$", message = "{credit.constraints.salary.Wrong.message}")
    @NotNull(message = "{credit.constraints.salary.NotBlank.message}")
    private Double salary;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Min(18)
    private int age;

    @Email
    private String email;

    @Transient
    @OneToMany(fetch = LAZY,mappedBy = "customer", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Credit> credits;

    public Customer(String identityNumber, String name, String surname, int age, String phoneNumber, Double salary, int creditScore, String email) {
        this.identityNumber = identityNumber;
        this.name = name;
        this.surname = surname;
        this.age=age;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.creditScore = creditScore;
        this.email=email;
    }

}
