package com.simplebank.bank.presentation.controller.port;

public record HttpRequest<T>(T body) {
}
