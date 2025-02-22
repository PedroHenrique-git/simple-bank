package com.simplebank.bank.infra.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Entity
@Table(name = "business_account")
public class BusinessAccountEntity extends AccountEntity
{
  public BusinessAccountEntity()
  {
    super();
  }
}
