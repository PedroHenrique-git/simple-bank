package com.simplebank.bank.infra.jpa.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Entity
@Table(name = "common_account")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AccountEntity
{
  @Id
  @GeneratedValue
  private Long id;

  @Nonnull
  private Double balance;

  @Nonnull
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserEntity user;

  @OneToMany(mappedBy = "payer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<TransactionEntity> payerTransactions = new ArrayList<>();

  @OneToMany(mappedBy = "payee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<TransactionEntity> payeeTransactions = new ArrayList<>();

  public AccountEntity()
  {
  }
}
