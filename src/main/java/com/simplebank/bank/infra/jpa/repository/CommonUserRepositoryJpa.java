package com.simplebank.bank.infra.jpa.repository;

import com.simplebank.bank.infra.jpa.entity.CommonUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonUserRepositoryJpa extends JpaRepository<CommonUserJpaEntity, Long> {
}
