package com.simplebank.bank.infra.jpa.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Entity
@Table(name = "business_user")
public class BusinessUserJpaEntity extends CommonUserJpaEntity {
    @Nonnull
    private String cnpj;
}
