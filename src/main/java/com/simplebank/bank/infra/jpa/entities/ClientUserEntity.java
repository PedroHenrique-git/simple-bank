package com.simplebank.bank.infra.jpa.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "client_user")
public class ClientUserEntity extends UserEntity
{
  @Nonnull
  @Column(unique = true)
  private String cpf;

  public ClientUserEntity()
  {
    super();
  }

  public ClientUserEntity(Long id, String name, String email, String password, String cpf,
                          AccountEntity account)
  {
    super(id, name, email, password, account);

    this.cpf = cpf;
  }

  @Override
  public String getDocument()
  {
    return this.cpf;
  }

  @Override
  public void setDocument(String cpf)
  {
    this.cpf = cpf;
  }
}
