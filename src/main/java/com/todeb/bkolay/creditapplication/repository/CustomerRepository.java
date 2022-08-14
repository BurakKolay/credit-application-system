package com.todeb.bkolay.creditapplication.repository;

import com.todeb.bkolay.creditapplication.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String > {

    boolean existsByIdentityNumber(String identityNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Customer> findByIdentityNumber(String identityNumber);


    void deleteByIdentityNumber(Optional<Customer> byId);
}
