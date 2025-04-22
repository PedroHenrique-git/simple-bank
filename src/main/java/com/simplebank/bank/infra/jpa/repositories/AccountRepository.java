package com.simplebank.bank.infra.jpa.repositories;

import com.simplebank.bank.infra.jpa.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<AccountEntity, Long>
{
  @Query(value = "select a from AccountEntity a where a.user.id = ?1", nativeQuery = false)
  AccountEntity findByUserId(Long id);
}
