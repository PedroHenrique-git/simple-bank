package com.simplebank.bank.domain.model.User;

public class BusinessUser extends CommonUser {
    private String cnpj;

    public BusinessUser() {
        super();
    }

    public BusinessUser(long id, String name, String email, String cnpj) {
        super(id, name, email);

        this.cnpj = cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }
}