package com.simplebank.bank.presentation.controller.port;

public record HttpResponse<T>(int status, boolean success, String message, T body) {
    
}
