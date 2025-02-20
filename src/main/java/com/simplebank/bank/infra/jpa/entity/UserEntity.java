package com.simplebank.bank.infra.jpa.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Entity
@Table(name = "common_user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Nonnull
    private String name;

    @Nonnull
    @Column(unique = true)
    private String email;

    @Nonnull
    private String password;

    @OneToOne(mappedBy = "user")
    private AccountEntity account;
}
