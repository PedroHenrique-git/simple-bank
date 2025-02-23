package com.simplebank.bank.infra.jpa.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "common_user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

  public abstract String getDocument();

  public abstract void setDocument(String document);
}
