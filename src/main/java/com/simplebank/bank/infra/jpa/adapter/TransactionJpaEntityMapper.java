package com.simplebank.bank.infra.jpa.adapter;

import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.infra.jpa.entity.TransactionJpaEntity;

public class TransactionJpaEntityMapper {
    private CommonAccountJpaEntityMapper accountMapper;

    public void setAccountMapper(CommonAccountJpaEntityMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public Transaction toModel(TransactionJpaEntity t) {
        return mapToConcreteModel(t);
    }

    public TransactionJpaEntity toEntity(Transaction t) {
        return mapToConcreteEntity(t);
    }

    private Transaction mapToConcreteModel(TransactionJpaEntity t) {
        return new Transaction(
                t.getId(),
                accountMapper.toModel(t.getPayer()),
                accountMapper.toModel(t.getPayee()),
                t.getValue()
        );
    }

    private TransactionJpaEntity mapToConcreteEntity(Transaction m) {
        return new TransactionJpaEntity(
                m.getId(),
                m.getValue(),
                accountMapper.toEntity(m.getPayer()),
                accountMapper.toEntity(m.getPayee())
        );
    }
}
