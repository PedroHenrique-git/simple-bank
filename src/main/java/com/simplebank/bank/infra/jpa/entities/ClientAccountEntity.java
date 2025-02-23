package com.simplebank.bank.infra.jpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "client_account")
public class ClientAccountEntity extends AccountEntity
{
  public ClientAccountEntity()
  {
    super();
  }

  public ClientAccountEntity(Long id,
                             Double balance,
                             UserEntity user,
                             List<TransactionEntity> payerTransactions,
                             List<TransactionEntity> payeeTransactions)
  {
    super(id, balance, user, payerTransactions, payeeTransactions);
  }
}
