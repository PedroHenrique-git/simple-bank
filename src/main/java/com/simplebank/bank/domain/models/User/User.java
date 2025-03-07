package com.simplebank.bank.domain.models.User;

public abstract class User
{
  protected long id;
  protected String name;
  protected String email;
  protected String password;

  protected User()
  {
  }

  protected User(long id, String name, String email, String password)
  {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPassword()
  {
    return this.password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public abstract String getDocument();

  public abstract void setDocument(String document);
}
