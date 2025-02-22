package com.simplebank.bank.infra.jpa.gateway;

import com.simplebank.bank.data.gateway.TransactionRepositoryGateway;
import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.infra.jpa.mapper.AccountEntityMapper;
import com.simplebank.bank.infra.jpa.mapper.TransactionEntityMapper;
import com.simplebank.bank.infra.jpa.repository.TransactionRepository;

public class TransactionRepositoryJpaGateway implements TransactionRepositoryGateway
{
  private final TransactionRepository repository;
  private final TransactionEntityMapper mapper;

  public TransactionRepositoryJpaGateway(TransactionRepository repository,
                                         TransactionEntityMapper mapper,
                                         AccountEntityMapper accountMapper)
  {
    this.repository = repository;
    this.mapper = mapper;

    this.mapper.setAccountMapper(accountMapper);
  }

  @Override
  public Transaction save(Transaction transaction)
  {
    var entity = mapper.toEntity(transaction);
    var createdTransaction = repository.save(entity);

    return mapper.toModel(createdTransaction);
  }
}
