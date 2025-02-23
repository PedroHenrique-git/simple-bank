package com.simplebank.bank.domain.models.User;

public class ClientUser extends User
{
  private String cpf;

  public ClientUser()
  {
    super();
  }

  public ClientUser(long id, String name, String email, String password, String cpf)
  {
    super(id, name, email, password);

    this.cpf = cpf;
  }

  @Override
  public String getDocument()
  {
    return this.cpf;
  }

  @Override
  public void setDocument(String document)
  {
    this.cpf = document;
  }
}
