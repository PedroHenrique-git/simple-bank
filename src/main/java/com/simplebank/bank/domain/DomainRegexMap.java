package com.simplebank.bank.domain;

public class DomainRegexMap
{
  public static final String CPF_OR_CNPJ =
      "^([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})$";
  public static final String CPF = "^[0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2}$";
  public static final String CNPJ =
      "^[0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2}$";
  public static final String EMAIL = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
  public static final String PASSWORD =
      "^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}$";
}
