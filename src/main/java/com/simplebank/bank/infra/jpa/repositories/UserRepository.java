package com.simplebank.bank.infra.jpa.repositories;

import com.simplebank.bank.infra.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
}
