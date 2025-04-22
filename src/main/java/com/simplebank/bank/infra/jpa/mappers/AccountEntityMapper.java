package com.simplebank.bank.infra.jpa.mappers;

import com.simplebank.bank.domain.factories.AccountFactoryMaker;
import com.simplebank.bank.domain.models.Account.Account;
import com.simplebank.bank.infra.jpa.entities.AccountEntity;
import com.simplebank.bank.infra.jpa.factories.AccountEntityFactoryMaker;

public class AccountEntityMapper
{
  private final AccountFactoryMaker accountFactoryMaker;
  private final AccountEntityFactoryMaker accountEntityFactoryMaker;
  private final UserEntityMapper userMapper;
  private final TransactionEntityMapper transactionMapper;

  public AccountEntityMapper(UserEntityMapper userMapper, AccountFactoryMaker accountFactoryMaker,
                             AccountEntityFactoryMaker accountEntityFactoryMaker,
                             TransactionEntityMapper transactionMapper)
  {
    this.userMapper = userMapper;
    this.accountFactoryMaker = accountFactoryMaker;
    this.accountEntityFactoryMaker = accountEntityFactoryMaker;
    this.transactionMapper = transactionMapper;
  }

  public Account toModel(AccountEntity a)
  {
    return mapToConcreteModel(a);
  }

  public AccountEntity toEntity(Account a, boolean includeId)
  {
    return mapToConcreteEntity(a, includeId);
  }

  private Account mapToConcreteModel(AccountEntity a)
  {
    var document = a.getUser().getDocument();
    var account = accountFactoryMaker.getFactory(document).make();

    account.setId(a.getId());
    account.setBalance(a.getBalance());
    account.setUser(userMapper.toModel(a.getUser()));
    
    account.setPayerTransactions(
        a.getPayerTransactions().stream().map(transactionMapper::toModel).toList());
    account.setPayeeTransactions(
        a.getPayeeTransactions().stream().map(transactionMapper::toModel).toList());

    return account;
  }

  private AccountEntity mapToConcreteEntity(Account a, boolean includeId)
  {
    var document = a.getUser().getDocument();
    var account = accountEntityFactoryMaker.getFactory(document).make();

    if (includeId)
    {
      account.setId(a.getId());
    }

    account.setBalance(a.getBalance());
    account.setUser(userMapper.toEntity(a.getUser(), includeId));

    return account;
  }
}
