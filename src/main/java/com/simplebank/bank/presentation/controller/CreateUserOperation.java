package com.simplebank.bank.presentation.controller;

import com.simplebank.bank.domain.exception.ValidationErrorException;
import com.simplebank.bank.presentation.controller.port.HttpRequest;
import com.simplebank.bank.presentation.controller.port.HttpResponse;
import com.simplebank.bank.usecase.UseCase;
import com.simplebank.bank.usecase.ports.UserDTORequest;
import com.simplebank.bank.usecase.ports.UserDTOResponse;

public class CreateUserOperation implements ControllerOperation<UserDTOResponse, UserDTORequest> {
    private final UseCase<UserDTORequest, UserDTOResponse> useCase;

    public CreateUserOperation(UseCase<UserDTORequest, UserDTOResponse> useCase) {
        this.useCase = useCase;
    }

    @Override
    public HttpResponse<UserDTOResponse> execute(HttpRequest<UserDTORequest> request) throws ValidationErrorException {
        var user = useCase.execute(request.body());

        return new HttpResponse<>(201, true, "user successfully registered", user);
    }
}
