package com.simplebank.bank.infra.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "business_account")
public class BusinessAccountEntity extends AccountEntity
{
  public BusinessAccountEntity()
  {
    super();
  }

  public BusinessAccountEntity(Long id,
                               Double balance,
                               UserEntity user,
                               List<TransactionEntity> payerTransactions,
                               List<TransactionEntity> payeeTransactions)
  {
    super(id, balance, user, payerTransactions, payeeTransactions);
  }
}
