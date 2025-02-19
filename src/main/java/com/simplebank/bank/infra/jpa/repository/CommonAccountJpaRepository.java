package com.simplebank.bank.infra.jpa.repository;

import com.simplebank.bank.infra.jpa.entity.CommonAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonAccountJpaRepository extends JpaRepository<CommonAccountJpaEntity, Long> {
}
