package com.simplebank.bank.infra.jpa.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@Entity
@Table(name = "common_account")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CommonAccountJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Nonnull
    private Double balance;

    @Nonnull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private CommonUserJpaEntity user;

    @OneToMany(mappedBy = "payer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TransactionJpaEntity> payerTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "payee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TransactionJpaEntity> payeeTransactions = new ArrayList<>();
}
