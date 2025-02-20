package com.simplebank.bank.infra.jpa.repository;

import com.simplebank.bank.infra.jpa.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
