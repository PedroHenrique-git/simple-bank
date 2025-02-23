package com.simplebank.bank.infra.jpa.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "business_user")
public class BusinessUserEntity extends UserEntity
{
  @Nonnull
  @Column(unique = true)
  private String cnpj;

  public BusinessUserEntity()
  {
    super();
  }

  public BusinessUserEntity(Long id, String name, String email, String password, String cnpj,
                            AccountEntity account)
  {
    super(id, name, email, password, account);

    this.cnpj = cnpj;
  }

  @Override
  public String getDocument()
  {
    return this.cnpj;
  }

  @Override
  public void setDocument(String document)
  {
    this.cnpj = document;
  }
}
