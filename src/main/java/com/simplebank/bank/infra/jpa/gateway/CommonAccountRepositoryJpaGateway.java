package com.simplebank.bank.infra.jpa.gateway;

import com.simplebank.bank.data.gateway.CommonAccountRepositoryGateway;
import com.simplebank.bank.domain.model.Account.CommonAccount;
import com.simplebank.bank.infra.jpa.adapter.CommonAccountJpaEntityMapper;
import com.simplebank.bank.infra.jpa.adapter.TransactionJpaEntityMapper;
import com.simplebank.bank.infra.jpa.repository.CommonAccountJpaRepository;

public class CommonAccountRepositoryJpaGateway implements CommonAccountRepositoryGateway {
    private final CommonAccountJpaRepository repository;
    private final CommonAccountJpaEntityMapper mapper;

    public CommonAccountRepositoryJpaGateway(CommonAccountJpaRepository repository, CommonAccountJpaEntityMapper mapper, TransactionJpaEntityMapper transactionMapper) {
        this.repository = repository;
        this.mapper = mapper;

        this.mapper.setTransactionMapper(transactionMapper);
    }

    @Override
    public CommonAccount save(CommonAccount account) {
        var entity = mapper.toEntity(account);
        var createdAccount = repository.save(entity);

        return mapper.toModel(createdAccount);
    }

    @Override
    public CommonAccount find(long id) {
        var entity = repository.findById(id);

        return entity.map(mapper::toModel).orElse(null);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
