package com.simplebank.bank.infra.jpa.mapper;

import com.simplebank.bank.domain.model.Account.BusinessAccount;
import com.simplebank.bank.domain.model.Account.ClientAccount;
import com.simplebank.bank.domain.model.Account.Account;
import com.simplebank.bank.infra.jpa.entity.*;
import lombok.Setter;

public class AccountEntityMapper {
    private final UserEntityMapper userMapper;

    @Setter
    private TransactionEntityMapper transactionMapper;

    public AccountEntityMapper(UserEntityMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Account toModel(AccountEntity a) {
        return mapToConcreteModel(a);
    }

    public AccountEntity toEntity(Account a) {
        return mapToConcreteEntity(a);
    }

    private Account mapToConcreteModel(AccountEntity entity) {
        if(entity instanceof BusinessAccountEntity a) {
            return new BusinessAccount(
                        a.getId(),
                        a.getBalance(),
                        userMapper.toModel(a.getUser()),
                        a.getPayerTransactions().stream().map(transactionMapper::toModel).toList(),
                        a.getPayeeTransactions().stream().map(transactionMapper::toModel).toList()
            );
        }

        if(entity instanceof ClientAccountEntity a) {
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

    private AccountEntity mapToConcreteEntity(Account model) {
        if(model instanceof BusinessAccount a) {
            return BusinessAccountEntity
                    .builder()
                    .id(a.getId())
                    .balance(a.getBalance())
                    .user(userMapper.toEntity(a.getUser()))
                    .payerTransactions(a.getPayerTransactions().stream().map(transactionMapper::toEntity).toList())
                    .payeeTransactions(a.getPayerTransactions().stream().map(transactionMapper::toEntity).toList())
                    .build();
        }

        if(model instanceof ClientAccount a) {
            return ClientAccountEntity
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
