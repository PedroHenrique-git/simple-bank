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
public abstract class AccountEntity {
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

    public AccountEntity() {}
}
