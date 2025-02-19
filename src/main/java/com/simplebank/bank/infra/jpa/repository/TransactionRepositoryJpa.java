package com.simplebank.bank.infra.jpa.repository;

import com.simplebank.bank.infra.jpa.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepositoryJpa extends JpaRepository<TransactionJpaEntity, Long> {
}
