package com.simplebank.bank.infra.jpa.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
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
public class TransactionEntity {
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
