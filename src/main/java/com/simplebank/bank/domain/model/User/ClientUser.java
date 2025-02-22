package com.simplebank.bank.domain.model.User;

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

  public String getCpf()
  {
    return cpf;
  }

  public void setCpf(String cpf)
  {
    this.cpf = cpf;
  }
}
