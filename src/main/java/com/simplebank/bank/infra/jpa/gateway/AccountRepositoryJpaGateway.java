package com.simplebank.bank.infra.jpa.gateway;

import com.simplebank.bank.data.gateway.AccountRepositoryGateway;
import com.simplebank.bank.domain.model.Account.Account;
import com.simplebank.bank.infra.jpa.mapper.AccountEntityMapper;
import com.simplebank.bank.infra.jpa.mapper.TransactionEntityMapper;
import com.simplebank.bank.infra.jpa.repository.AccountRepository;

public class AccountRepositoryJpaGateway implements AccountRepositoryGateway {
    private final AccountRepository repository;
    private final AccountEntityMapper mapper;

    public AccountRepositoryJpaGateway(AccountRepository repository, AccountEntityMapper mapper, TransactionEntityMapper transactionMapper) {
        this.repository = repository;
        this.mapper = mapper;

        this.mapper.setTransactionMapper(transactionMapper);
    }

    @Override
    public Account save(Account account) {
        var entity = mapper.toEntity(account);
        var createdAccount = repository.save(entity);

        return mapper.toModel(createdAccount);
    }

    @Override
    public Account find(long id) {
        var entity = repository.findById(id);

        return entity.map(mapper::toModel).orElse(null);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
