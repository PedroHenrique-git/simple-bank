package com.simplebank.bank.infra.jpa.repositories;

import com.simplebank.bank.infra.jpa.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>
{
}
