package com.simplebank.bank.infra.jpa.gateways;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.models.Account.Account;
import com.simplebank.bank.infra.jpa.mappers.AccountEntityMapper;
import com.simplebank.bank.infra.jpa.mappers.TransactionEntityMapper;
import com.simplebank.bank.infra.jpa.repositories.AccountRepository;

public class AccountRepositoryJpaGateway implements AccountRepositoryGateway
{
  private final AccountRepository repository;
  private final AccountEntityMapper mapper;

  public AccountRepositoryJpaGateway(AccountRepository repository,
                                     AccountEntityMapper mapper,
                                     TransactionEntityMapper transactionMapper)
  {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Account save(Account account)
  {
    var entity = mapper.toEntity(account, false);
    var createdAccount = repository.save(entity);

    return mapper.toModel(createdAccount);
  }

  @Override
  public Account update(Account account)
  {
    var entity = mapper.toEntity(account, true);
    var updatedAccount = repository.save(entity);

    return mapper.toModel(updatedAccount);
  }

  @Override
  public Account find(long id)
  {
    var entity = repository.findById(id);

    return entity.map(mapper::toModel).orElse(null);
  }

  @Override
  public Account findByUserId(long id)
  {
    var entity = repository.findByUserId(id);

    return mapper.toModel(entity);
  }

  @Override
  public void delete(long id)
  {
    repository.deleteById(id);
  }
}
