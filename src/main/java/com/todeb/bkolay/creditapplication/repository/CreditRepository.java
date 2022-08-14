package com.todeb.bkolay.creditapplication.repository;

import com.todeb.bkolay.creditapplication.model.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Long> {
}
