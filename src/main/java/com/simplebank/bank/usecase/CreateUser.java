package com.simplebank.bank.usecase;

import com.simplebank.bank.data.gateway.UserRepositoryGateway;
import com.simplebank.bank.domain.exception.ValidationErrorException;
import com.simplebank.bank.usecase.mapper.UserDTOMapper;
import com.simplebank.bank.usecase.ports.UserDTORequest;
import com.simplebank.bank.usecase.ports.CreateUserInputValidator;
import com.simplebank.bank.usecase.ports.UserDTOResponse;

public class CreateUser implements UseCase<UserDTORequest, UserDTOResponse> {
    private final CreateUserInputValidator validator;
    private final UserDTOMapper mapper;
    private final UserRepositoryGateway userRepository;

    public CreateUser(UserRepositoryGateway userRepository, CreateUserInputValidator validator, UserDTOMapper mapper) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public UserDTOResponse execute(UserDTORequest dto) throws ValidationErrorException {
        var violations = validator.validate(dto);

        if(!violations.isEmpty()) {
            throw new ValidationErrorException("Invalid User input", violations);
        }

        var createdUser = userRepository.save(mapper.toUser(dto));

        return mapper.toResponse(createdUser);
    }
}
