package com.simplebank.bank.infra.jpa.gateway;

import com.simplebank.bank.data.gateway.CommonUserRepositoryGateway;
import com.simplebank.bank.domain.model.User.CommonUser;
import com.simplebank.bank.infra.jpa.adapter.CommonUserJpaEntityMapper;
import com.simplebank.bank.infra.jpa.repository.CommonUserRepositoryJpa;

public class CommonUserRepositoryJpaGateway implements CommonUserRepositoryGateway {
    private final CommonUserRepositoryJpa repository;
    private final CommonUserJpaEntityMapper mapper;

    public CommonUserRepositoryJpaGateway(CommonUserRepositoryJpa repository, CommonUserJpaEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CommonUser save(CommonUser user) {
        var entity = mapper.toEntity(user);
        var createdUser = repository.save(entity);

        return mapper.toModel(createdUser);
    }

    @Override
    public CommonUser update(long id, CommonUser newUser) {
        var user = find(id);

        if(user == null) {
            return null;
        }

        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setPassword(newUser.getPassword());

        var entity = mapper.toEntity(user);
        var updatedUser = repository.save(entity);

        return mapper.toModel(updatedUser);
    }

    @Override
    public CommonUser find(long id) {
        var entity = repository.findById(id);

        return entity.map(mapper::toModel).orElse(null);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
