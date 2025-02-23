package com.simplebank.bank.infra.jpa.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transaction")
public class TransactionEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Nonnull
  private Double value;

  @Nonnull
  @ManyToOne
  @JoinColumn(name = "payer_id", updatable = false, insertable = false)
  private AccountEntity payer;

  @Nonnull
  @ManyToOne
  @JoinColumn(name = "payee_id", updatable = false, insertable = false)
  private AccountEntity payee;
}
