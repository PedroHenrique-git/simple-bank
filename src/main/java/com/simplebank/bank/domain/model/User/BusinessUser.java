package com.simplebank.bank.domain.model.User;

public class BusinessUser extends User {
    private String cnpj;

    public BusinessUser() {
        super();
    }

    public BusinessUser(long id, String name, String email, String password, String cnpj) {
        super(id, name, email, password);

        this.cnpj = cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }
}