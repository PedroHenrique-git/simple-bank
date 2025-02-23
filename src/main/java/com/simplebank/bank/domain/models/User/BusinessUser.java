package com.simplebank.bank.domain.models.User;

public class BusinessUser extends User
{
  private String cnpj;

  public BusinessUser()
  {
    super();
  }

  public BusinessUser(long id, String name, String email, String password, String cnpj)
  {
    super(id, name, email, password);

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