package com.simplebank.bank.infra.jpa.adapter;

import com.simplebank.bank.domain.model.Account.BusinessAccount;
import com.simplebank.bank.domain.model.Account.ClientAccount;
import com.simplebank.bank.domain.model.Account.CommonAccount;
import com.simplebank.bank.infra.jpa.entity.*;

public class CommonAccountJpaEntityMapper {
    private final CommonUserJpaEntityMapper userMapper;
    private TransactionJpaEntityMapper transactionMapper;

    public CommonAccountJpaEntityMapper(CommonUserJpaEntityMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setTransactionMapper(TransactionJpaEntityMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    public CommonAccount toModel(CommonAccountJpaEntity a) {
        return mapToConcreteModel(a);
    }

    public CommonAccountJpaEntity toEntity(CommonAccount a) {
        return mapToConcreteEntity(a);
    }

    private CommonAccount mapToConcreteModel(CommonAccountJpaEntity entity) {
        if(entity instanceof BusinessAccountJpaEntity a) {
            return new BusinessAccount(
                        a.getId(),
                        a.getBalance(),
                        userMapper.toModel(a.getUser()),
                        a.getPayerTransactions().stream().map(transactionMapper::toModel).toList(),
                        a.getPayeeTransactions().stream().map(transactionMapper::toModel).toList()
            );
        }

        if(entity instanceof ClientAccountJpaEntity a) {
            return new ClientAccount(
                    a.getId(),
                    a.getBalance(),
                    userMapper.toModel(a.getUser()),
                    a.getPayerTransactions().stream().map(transactionMapper::toModel).toList(),
                    a.getPayeeTransactions().stream().map(transactionMapper::toModel).toList()
            );
        }

        return null;
    }

    private CommonAccountJpaEntity mapToConcreteEntity(CommonAccount model) {
        if(model instanceof BusinessAccount a) {
            return BusinessAccountJpaEntity
                    .builder()
                    .id(a.getId())
                    .balance(a.getBalance())
                    .user(userMapper.toEntity(a.getUser()))
                    .payerTransactions(a.getPayerTransactions().stream().map(transactionMapper::toEntity).toList())
                    .payeeTransactions(a.getPayerTransactions().stream().map(transactionMapper::toEntity).toList())
                    .build();
        }

        if(model instanceof ClientAccount a) {
            return ClientAccountJpaEntity
                    .builder()
                    .id(a.getId())
                    .balance(a.getBalance())
                    .user(userMapper.toEntity(a.getUser()))
                    .payerTransactions(a.getPayerTransactions().stream().map(transactionMapper::toEntity).toList())
                    .payeeTransactions(a.getPayerTransactions().stream().map(transactionMapper::toEntity).toList())
                    .build();
        }

        return null;
    }
}
