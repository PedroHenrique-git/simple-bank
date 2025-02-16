package com.simplebank.bank.domain.model.User;

public class Client extends User {
    private String cpf;

    public Client() {
        super();
    }

    public Client(long id, String name, String email, String cpf) {
        super(id, name, email);

        this.cpf = cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
