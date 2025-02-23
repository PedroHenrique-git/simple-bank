package com.simplebank.bank.infra.jpa.repositories;

import com.simplebank.bank.infra.jpa.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>
{
}
