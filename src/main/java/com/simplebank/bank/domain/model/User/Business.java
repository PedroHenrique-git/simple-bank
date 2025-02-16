package com.simplebank.bank.domain.model.User;

public class Business extends User {
    private String cnpj;

    public Business() {
        super();
    }

    public Business(long id, String name, String email, String cnpj) {
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