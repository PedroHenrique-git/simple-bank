package com.simplebank.bank.infra.jpa.gateway;

import com.simplebank.bank.data.gateway.TransactionRepositoryGateway;
import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.infra.jpa.adapter.CommonAccountJpaEntityMapper;
import com.simplebank.bank.infra.jpa.adapter.TransactionJpaEntityMapper;
import com.simplebank.bank.infra.jpa.repository.TransactionRepositoryJpa;

public class TransactionRepositoryJpaGateway implements TransactionRepositoryGateway {
    private final TransactionRepositoryJpa repository;
    private final TransactionJpaEntityMapper mapper;

    public TransactionRepositoryJpaGateway(TransactionRepositoryJpa repository, TransactionJpaEntityMapper mapper, CommonAccountJpaEntityMapper accountMapper) {
        this.repository = repository;
        this.mapper = mapper;

        this.mapper.setAccountMapper(accountMapper);
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = mapper.toEntity(transaction);
        var createdTransaction = repository.save(entity);

        return mapper.toModel(createdTransaction);
    }
}
