package com.simplebank.bank.usecase.ports;

public record UserDTORequest(String name, String email, String password, String document) {
}
