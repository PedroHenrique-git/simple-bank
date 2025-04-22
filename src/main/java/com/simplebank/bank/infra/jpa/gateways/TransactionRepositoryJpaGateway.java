package com.simplebank.bank.infra.jpa.gateways;

import com.simplebank.bank.data.gateways.TransactionRepositoryGateway;
import com.simplebank.bank.domain.models.Transaction.Transaction;
import com.simplebank.bank.infra.jpa.mappers.TransactionEntityMapper;
import com.simplebank.bank.infra.jpa.repositories.TransactionRepository;

public class TransactionRepositoryJpaGateway implements TransactionRepositoryGateway
{
  private final TransactionRepository repository;
  private final TransactionEntityMapper mapper;

  public TransactionRepositoryJpaGateway(TransactionRepository repository,
                                         TransactionEntityMapper mapper)
  {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Transaction save(Transaction transaction)
  {
    var entity = mapper.toEntity(transaction);
    var createdTransaction = repository.save(entity);

    return mapper.toModel(createdTransaction);
  }
}
