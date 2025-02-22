package com.simplebank.bank.infra.jpa.repository;

import com.simplebank.bank.infra.jpa.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>
{
}
