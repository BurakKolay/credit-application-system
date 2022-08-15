package com.todeb.bkolay.creditapplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {

    @Column(unique = true, name = "identity_number")
    @Pattern(regexp = "[1-9]\\d{10}", message = "{credit.constraints.identityNumber.Wrong.message}")
    private String identityNumber;

    private String name;
    private String surname;
    private Double salary;
    private String phoneNumber;
    private Integer creditScore;
    private Integer age;
    private String email;
}
