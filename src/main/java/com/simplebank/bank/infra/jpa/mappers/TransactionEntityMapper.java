package com.simplebank.bank.infra.jpa.mappers;

import com.simplebank.bank.domain.models.Transaction.Transaction;
import com.simplebank.bank.infra.jpa.entities.TransactionEntity;
import lombok.Setter;

@Setter
public class TransactionEntityMapper
{
  private AccountEntityMapper accountMapper;

  public Transaction toModel(TransactionEntity t)
  {
    return mapToConcreteModel(t);
  }

  public TransactionEntity toEntity(Transaction t)
  {
    return mapToConcreteEntity(t);
  }

  private Transaction mapToConcreteModel(TransactionEntity t)
  {
    return new Transaction(
        t.getId(),
        accountMapper.toModel(t.getPayer()),
        accountMapper.toModel(t.getPayee()),
        t.getValue()
    );
  }

  private TransactionEntity mapToConcreteEntity(Transaction m)
  {
    return new TransactionEntity(m.getId(), m.getValue(),
        accountMapper.toEntity(m.getPayer(), false),
        accountMapper.toEntity(m.getPayee(), false));
  }
}
