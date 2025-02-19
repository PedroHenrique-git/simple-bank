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
public class TransactionJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Nonnull
    private Long value;

    @Nonnull
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private CommonAccountJpaEntity payer;

    @Nonnull
    @ManyToOne
    @JoinColumn(name = "payee_id")
    private CommonAccountJpaEntity payee;
}
