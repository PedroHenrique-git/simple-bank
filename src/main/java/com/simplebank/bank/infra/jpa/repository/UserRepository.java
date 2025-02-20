package com.simplebank.bank.infra.jpa.repository;

import com.simplebank.bank.infra.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
