package com.simplebank.bank.infra.jpa.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class TransactionEntity
{
  @Id
  @GeneratedValue
  private Long id;

  @Nonnull
  private Double value;

  @Nonnull
  @ManyToOne
  @JoinColumn(name = "payer_id")
  private AccountEntity payer;

  @Nonnull
  @ManyToOne
  @JoinColumn(name = "payee_id")
  private AccountEntity payee;
}
